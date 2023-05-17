package ifmo.dma.apigateway.dto

import jakarta.validation.constraints.NotBlank

/**
 * данные для авторизации пользователя
 */
data class AccountCreds (
    val login: String,
    val password: String
)


/**
 */
data class GroupCreds (
    val groupId: Int,
    val groupName: String,
    val inviteCode: String
)

/**
 */
data class QueueCreds (
    val id: Int,
    val queueName: String,
    val recordsNumber: Int
)


/**
 * Список очередей группы без содержания
 */
class QueueList (
    val queues: List<QueueCreds>
)

/**
 * Данные для регистрации нового пользователя
 */
data class RegisterRequest (
    @get:NotBlank
    val login: String,
    @get:NotBlank
    val password: String,
    @get:NotBlank
    val fullName: String
)

/**
 * Отвечает за представление человека в списке очереди
 */
data class StudentInQueueCreds (
    val fullName: String
)

/**
 * Состав очереди
 */
data class StudentsInQueueList (
    val students: List<StudentInQueueCreds>
)

/**
 * Предлагаю активно пользоваться этим методом для превращения любого класса выше в мап
 */
fun anyToMap(obj: Any): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val fields = obj::class.java.declaredFields
    for (field in fields) {
        field.isAccessible = true
        map[field.name] = field.get(obj) ?: continue
    }
    return map
}
