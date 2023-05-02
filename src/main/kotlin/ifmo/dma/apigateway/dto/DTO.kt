package ifmo.dma.apigateway.dto

/**
 * данные для авторизации пользователя
 */
data class AccountCreds (
    val login: String,
    val password: String
)


/**
 * queue create request
 */
data class CreateQueueRequest (
    val queueName: String
)


/**
 * creds to join existing group
 */
data class EnterGroupRequest (
    val groupCode: String
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
    val login: String,
    val password: String,
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

fun anyToMap(obj: Any): Map<String, Any> {
    val map = mutableMapOf<String, Any>()
    val fields = obj::class.java.declaredFields
    for (field in fields) {
        field.isAccessible = true
        map[field.name] = field.get(obj) ?: continue
    }
    return map
}
