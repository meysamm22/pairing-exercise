package io.billie.countries.infrastructure.repository

import io.billie.countries.domain.model.City
import io.billie.countries.domain.repositoryinterface.CityRepositoryInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Repository
internal class CityRepository : CityRepositoryInterface {


    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Transactional(readOnly = true)
    override fun findAllByCountryCode(countryCode: String): List<City> {
        val rows: List<Map<String, Any>> = jdbcTemplate.queryForList(
            "select id, name, country_code from organisations_schema.cities where country_code = ?",
            countryCode
        )

        return mapToCities(rows)
    }

    private fun mapToCities(rows: List<Map<String, Any>>): List<City> {
        return rows.mapNotNull { row ->
            runCatching {
                City.Companion.create(
                    row["id"] as UUID,
                    row["name"] as String,
                    row["country_code"] as String
                )
            }.getOrNull()
        }
    }
}