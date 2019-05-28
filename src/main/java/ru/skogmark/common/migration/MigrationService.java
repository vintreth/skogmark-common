package ru.skogmark.common.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

public class MigrationService implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(MigrationService.class);

    private final TransactionTemplate transactionTemplate;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Set<String> migrationResources;

    public MigrationService(@Nonnull TransactionTemplate transactionTemplate,
                            @Nonnull NamedParameterJdbcTemplate jdbcTemplate,
                            @Nullable Set<String> migrationResources) {
        this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
        this.jdbcTemplate = requireNonNull(jdbcTemplate, "jdbcTemplate");
        this.migrationResources = nonNull(migrationResources)
                ? Collections.unmodifiableSet(new HashSet<>(migrationResources))
                : Collections.emptySet();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Starting applying migrations to database");
        migrationResources.forEach(this::migrate);
        log.info("Migrations finished");
    }

    private void migrate(String migrationResource) {
        log.info("Applying migration: resource={}", migrationResource);
        try {
            String sql = getMigrationFileContent(migrationResource);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    jdbcTemplate.getJdbcOperations().execute(sql);
                }
            });
        } catch (IOException e) {
            log.warn("Failure to apply migration: resource=" + migrationResource, e);
        }
    }

    static String getMigrationFileContent(String resourceFile) throws IOException {
        try (InputStream inputStream = MigrationService.class.getResourceAsStream(resourceFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder sql = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                sql.append(line);
            }
            return sql.toString();
        }
    }
}
