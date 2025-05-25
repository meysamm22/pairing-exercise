package io.billie.merchants.infrastructure

import io.billie.merchants.domain.MerchantFactory
import io.billie.merchants.domain.model.Merchant
import io.billie.merchants.domain.repositoryinterface.MerchantRepositoryInterface
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.sql.ResultSet
import java.util.UUID

@Repository
internal class MerchantRepository: MerchantRepositoryInterface {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    private lateinit var merchantFactory: MerchantFactory

    @Transactional(readOnly = true)
    override fun findAll(): List<Merchant> {
        val dtos: List<MerchantDatabaseDto> = jdbcTemplate.query(merchantQuery(), merchantDtoMapper())

        return dtos.map { dto -> merchantFactory.createFromDto(dto) }
    }

    @Transactional
    override fun create(merchant: Merchant): UUID {
        val id: UUID = createContactDetails(merchant)
        return createMerchant(merchant, id)
    }

    @Transactional(readOnly = true)
    override fun findById(id: UUID): Merchant? {
        val dto: MerchantDatabaseDto? = jdbcTemplate.queryForObject(
            "select " +
                    "o.id as id, " +
                    "o.name as name, " +
                    "o.date_founded as date_founded, " +
                    "o.country_code as country_code, " +
                    "o.VAT_number as VAT_number, " +
                    "o.registration_number as registration_number," +
                    "o.legal_entity_type as legal_entity_type," +
                    "o.contact_details_id as contact_details_id, " +
                    "from " +
                    "organisations_schema.organisations o " +
                    "WHERE id = ?",
            merchantDtoMapper(),
            id.toString()
        )
        if (dto == null) {
            return null
        }

        return merchantFactory.createFromDto(dto);
    }

    private fun createMerchant(org: Merchant, contactDetailsId: UUID): UUID {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            { connection ->
                val ps = connection.prepareStatement(
                    "INSERT INTO organisations_schema.organisations (" +
                            "name, " +
                            "date_founded, " +
                            "country_code, " +
                            "vat_number, " +
                            "registration_number, " +
                            "legal_entity_type, " +
                            "contact_details_id" +
                            ") VALUES (?, ?, ?, ?, ?, ?, ?)",
                    arrayOf("id")
                )
                ps.setString(1, org.name)
                ps.setDate(2, Date.valueOf(org.dateFounded))
                ps.setString(3, org.country.code)
                ps.setString(4, org.vatNumber)
                ps.setString(5, org.registrationNumber)
                ps.setString(6, org.legalType.toString())
                ps.setObject(7, contactDetailsId)
                ps
            }, keyHolder
        )
        return keyHolder.getKeyAs(UUID::class.java)!!
    }

    private fun createContactDetails(merchant: Merchant): UUID {
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            { connection ->
                val ps = connection.prepareStatement(
                    "insert into organisations_schema.contact_details " +
                            "(" +
                            "phone_number, " +
                            "fax, " +
                            "email" +
                            ") values(?,?,?)",
                    arrayOf("id")
                )
                ps.setString(1, merchant.contactDetails.phoneNumber)
                ps.setString(2, merchant.contactDetails.fax)
                ps.setString(3, merchant.contactDetails.email)
                ps
            },
            keyHolder
        )
        return keyHolder.getKeyAs(UUID::class.java)!!
    }

    private fun merchantQuery() = "select " +
            "o.id as id, " +
            "o.name as name, " +
            "o.date_founded as date_founded, " +
            "o.country_code as country_code, " +
            "o.VAT_number as VAT_number, " +
            "o.registration_number as registration_number," +
            "o.legal_entity_type as legal_entity_type," +
            "o.contact_details_id as contact_details_id, " +
            "cd.phone_number as phone_number, " +
            "cd.fax as fax, " +
            "cd.email as email " +
            "from " +
            "organisations_schema.organisations o " +
            "INNER JOIN organisations_schema.contact_details cd on o.contact_details_id::uuid = cd.id::uuid "

    private fun merchantDtoMapper() = RowMapper<MerchantDatabaseDto> {
        it: ResultSet, _: Int ->
            MerchantDatabaseDto(
            it.getObject("id", UUID::class.java),
            it.getString("name"),
            Date(it.getDate("date_founded").time).toLocalDate(),
            it.getString("country_code"),
            it.getString("vat_number"),
            it.getString("registration_number"),
            it.getString("legal_entity_type"),
            UUID.fromString(it.getString("contact_details_id")),
            it.getString("fax"),
            it.getString("email"),
            it.getString("phone_number")
        )
    }
}