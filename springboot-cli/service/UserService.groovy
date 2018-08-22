import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate

    List<User> findAll() {
        jdbcTemplate.query(
                "select id, age, name, birthday from user",
                { rs, row ->
                    new User(id: rs.getLong(1),
                        age: rs.getLong(2),
                        name: rs.getString(3),
                        birthday: rs.getString(4))
                } as RowMapper
        )
    }

    def insertByUser(User user) {
        jdbcTemplate.update(
                "insert into user(age, name, birthday) values(?,?,?)",
                user.age,
                user.name,
                user.birthday
        )
    }

    User findById(Long id) {
        jdbcTemplate.queryForObject(
                "select id, age, name, birthday from user where id = ?",
                { rs, row ->
                    new User(id: rs.getLong(1),
                            age: rs.getLong(2),
                            name: rs.getString(3),
                            birthday: rs.getString(4))
                } as RowMapper,
                id
        )
    }

    def update(User user) {
        jdbcTemplate.update(
                "update user set age=?, name=?, birthday=? where id=?",
                user.age,
                user.name,
                user.birthday,
                user.id
        )
    }

    def delete(Long id) {
        jdbcTemplate.update(
                "delete from user where id = ?",
                id
        )
    }
}