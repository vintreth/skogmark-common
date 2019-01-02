package ru.skogmark.common.migration;

import org.junit.Test;

import static org.junit.Assert.*;

public class MigrationServiceTest {
    @Test
    public void shouldReturnContentForMigrationFiles() throws Exception {
        String test0 = MigrationService.getMigrationFileContent("test-migration-0.sql");
        assertNotNull(test0);
        assertFalse(test0.isEmpty());
        assertEquals("create table if not exists test0 (id bigserial primary key, name varchar(128) not null);",
                test0);

        String test1 = MigrationService.getMigrationFileContent("test-migration-1.sql");
        assertNotNull(test1);
        assertFalse(test1.isEmpty());
        assertEquals("create table if not exists test1 (id bigserial primary key, text text);", test1);
    }
}