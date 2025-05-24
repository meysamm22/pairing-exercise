package io.billie.countries.infrastructure.repository

import io.billie.countries.domain.model.Country
import io.billie.countries.domain.repositoryinterface.CountryRepositoryInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
internal class CountryRepository : CountryRepositoryInterface {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Transactional(readOnly=true)
    override fun findAll(): List<Country> {
        val rows: List<Map<String, Any>> = jdbcTemplate.queryForList(
            "select id, name, country_code from organisations_schema.countries"
        )

        return mapToCountries(rows)
    }

    private fun mapToCountries(rows: List<Map<String, Any>>): List<Country> {
        val countries: List<Country> = rows.mapNotNull { row ->
            runCatching {
                Country.Companion.create(
                    row["id"] as UUID,
                    row["name"] as String,
                    row["country_code"] as String
                )
            }.getOrNull()
        }
        return countries
    }
}