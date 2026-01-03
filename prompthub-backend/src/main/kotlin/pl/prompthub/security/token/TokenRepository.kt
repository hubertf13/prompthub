package pl.prompthub.security.token

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TokenRepository : JpaRepository<Token, Long> {

    @Query(
        """
        select t from Token t inner join User u on t.user.id = u.id
        where u.id = :id and (t.expired = false or t.revoked = false)
        """
    )
    fun findAllValidTokensByUser(id: Long): List<Token>

    fun findByToken(token: String): Token?
}
