package ru.romanow.protocols.restful.web

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.Future


@RestController
@RequestMapping("/api/v1/tasks")
class TaskController(private val taskService: TaskService) {

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun accept(@RequestBody request: Map<String, Double>): UUID {
        return taskService.accept { Thread.sleep(10000) }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{operationId}/cancel")
    fun cancelOrder(@PathVariable operationId: UUID) {
        taskService.cancel(operationId)
    }
}

@Service
class TaskService {
    private val executorService = Executors.newCachedThreadPool()
    private val operations = ConcurrentHashMap<UUID, Future<*>>()

    fun accept(task: Runnable): UUID {
        val operationId = UUID.randomUUID()
        val future = executorService.submit {
            try {
                task.run()
            } catch (exception: InterruptedException) {
                println("Task $operationId cancelled")
            }
        }
        operations[operationId] = future
        return operationId
    }

    fun cancel(operationId: UUID) {
        operations.remove(operationId)?.cancel(true)
    }
}
