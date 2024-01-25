package ie.fieldublin.config;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    private final boolean BASELINE_ON_MIGRATE = true;
    private final String BASELINE_VERSION = "0";
    private final String LOCATION = "classpath:db/migration";
    private final boolean PLACEHOLDER_REPLACEMENT = true;

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flywayMigrationStrategy -> {
            Flyway flyway = Flyway.configure()
                    .baselineOnMigrate(BASELINE_ON_MIGRATE)
                    .baselineVersion(BASELINE_VERSION)
                    .locations(LOCATION)
                    .placeholderReplacement(PLACEHOLDER_REPLACEMENT)
                    .dataSource(flywayMigrationStrategy.getConfiguration().getDataSource())
                    .load();

            flyway.repair();
            flyway.migrate();
        };
    }

}
