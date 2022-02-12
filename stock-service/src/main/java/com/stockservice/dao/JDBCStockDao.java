package com.stockservice.dao;

import com.stockservice.domain.StockEntity;
import com.stockservice.exception.MySQLException;
import com.stockservice.exception.StockNotExistingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Component
public class JDBCStockDao implements StockDao {
    private final int lockTimeoutSeconds = 10;
    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void insert(StockEntity stockEntity) {
        try {
            this.jdbcTemplate.update(
                    String.format("INSERT INTO stock(product_id, stock) VALUES('%s', %s)", stockEntity.getProductId(), stockEntity.getStock())
            );
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("데이터 추가 오류.");
        }
    }

    @Override
    public void updateStockWithLock(String productId, Long quantity) {
        String lockName = "updateLock";
        try (Connection connection = dataSource.getConnection()) {
            try {
                getLock(connection, lockName, this.lockTimeoutSeconds);
                this.jdbcTemplate.update(
                        String.format("UPDATE stock SET stock = stock + %s WHERE product_id = '%s'", quantity.toString(), productId)
                );
            } finally {
                releaseLock(connection, lockName);
            }
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("데이터 베이스 오류.");
        }
    }

    @Override
    public void updateStock(String productId, Long quantity) {
        try {
            this.jdbcTemplate.update(
                    String.format("UPDATE stock SET stock = stock + %s WHERE product_id = '%s'", quantity.toString(), productId)
            );
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("stock 업데이트 오류.");
        }
    }

    @Override
    public boolean checkWithLock(String productId, Long quantity) {
        String lockName = "checkLock";
        try (Connection connection = dataSource.getConnection()) {
            try {
                getLock(connection, lockName, this.lockTimeoutSeconds);
                Optional<Long> optionalStock = findStockByProductId(productId);
                Long stock = optionalStock.orElseThrow(
                        () -> new StockNotExistingException("존재하지 않는 상품 정보 오류.")
                );
                if (stock - quantity >= 0L) {
                    this.jdbcTemplate.update(
                            String.format("UPDATE stock SET stock = stock - %s WHERE product_id = '%s'", quantity.toString(), productId)
                    );
                    return true;
                }
                return false;

            } finally {
                releaseLock(connection, lockName);
            }
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("데이터 베이스 오류.");
        }
    }

    @Override
    public Optional<Long> findStockByProductId(String productId) {
        Long stock = null;
        try {
            stock = this.jdbcTemplate.queryForObject(
                    String.format("SELECT stock FROM stock WHERE product_id = '%s'", productId),
                    Long.class
            );
        } catch (Exception e) {
            if(e.getClass().getSimpleName().equals("EmptyResultDataAccessException"))
                return Optional.ofNullable(null);

            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("데이터 베이스 오류.");
        }
        return Optional.ofNullable(stock);
    }

    @Override
    public void deleteByProductId(String productId) {
        try {
            this.jdbcTemplate.update(
                    String.format("Delete FROM stock WHERE product_id = '%s'", productId)
            );
        } catch (Exception e) {
            log.error("name: " + e.getClass().getSimpleName() + "\nmsg :" + e.getMessage());
            throw new MySQLException("stock 삭제 오류.");
        }
    }

    private void getLock(Connection connection, String lockName, int timeoutSeconds) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT GET_LOCK(?, ?)")) {
            preparedStatement.setString(1, lockName);
            preparedStatement.setInt(2, timeoutSeconds);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new MySQLException("데이터 베이스 락 취득 불가 오류.");
                }

                int result = resultSet.getInt(1);
                if (result != 1) {
                    throw new MySQLException("데이터 베이스 락 취득 불가 오류.");
                }
            }
        }
    }

    private void releaseLock(Connection connection, String lockName) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT RELEASE_LOCK(?)")) {
            preparedStatement.setString(1, lockName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new MySQLException("데이터 베이스 락 해제 불가 오류.");
                }

                int result = resultSet.getInt(1);
                if (result != 1) {
                    throw new MySQLException("데이터 베이스 락 해제 불가 오류.");
                }
            }
        }
    }

    public JDBCStockDao(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
}
