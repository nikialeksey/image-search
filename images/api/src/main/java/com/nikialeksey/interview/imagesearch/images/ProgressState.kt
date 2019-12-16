package com.nikialeksey.interview.imagesearch.images

data class ProgressState(
    val status: Status,
    val message: String = ""
) {
    enum class Status {
        IN_PROGRESS,
        SUCCESS,
        FAILED
    }

    fun isSuccess(): Boolean {
        return status == Status.SUCCESS
    }

    fun isInProgress(): Boolean {
        return status == Status.IN_PROGRESS
    }

    fun isFailed(): Boolean {
        return status == Status.FAILED
    }

    companion object {
        fun inProgress(): ProgressState {
            return ProgressState(Status.IN_PROGRESS)
        }

        fun success(): ProgressState {
            return ProgressState(Status.SUCCESS)
        }

        fun failed(message: String): ProgressState {
            return ProgressState(Status.FAILED, message)
        }
    }
}