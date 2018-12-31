package org.motus.workouts

import org.springframework.dao.DataIntegrityViolationException
import org.apache.shiro.SecurityUtils
import org.motus.log.SkippedWorkoutLog
import org.motus.Account
import org.motus.BaseController
import org.motus.AppConstants

@Mixin(BaseController)
class ChestBackController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def index() {
        redirect(action: "list", params: params)
    }


	def entry(Long id){
		withPlannedWorkout { chestBackInstance ->
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.ENTRY_VIEW)
		}
	}
	
	def changeDate(Long id){
		withPlannedWorkout { chestBackInstance ->
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.CHANGE_DATE_VIEW)
		}
	}	
	
    def list(Integer max) {
		authenticated { subject -> 
       		params.max = Math.min(max ?: 10, 100)
        	[chestBackInstanceList: ChestBack.list(params), chestBackInstanceTotal: ChestBack.count()]
		}
    }


    def show(Long id) {
		withPlannedWorkout { chestBackInstance ->
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.SHOW_VIEW)
		}
    }


	def confirmSkip(Long id){
		withPlannedWorkout { chestBackInstance ->
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.CONFIRM_SKIP_VIEW)
		}
	}
	
	
	def skip(Long id){
		withPlannedWorkoutSkipped { chestBackInstance ->
			flash.message = AppConstants.SKIP_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}

	
	def confirmRecover(Long id){
		withPlannedWorkout { chestBackInstance ->
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.CONFIRM_RECOVER_VIEW)
		}
	}
	
	
	def recover(Long id){
		withPlannedWorkoutRecovered { chestBackInstance ->
			flash.message = AppConstants.RECOVERY_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}
	

    def update(Long id, Long version) {
		withPlannedWorkoutUpdated { chestBackInstance ->
			flash.message = AppConstants.UPDATE_SUCCESS_MESSAGE
			request.plannedWorkout = chestBackInstance
			render(view : AppConstants.SHOW_VIEW)
		}
	}
	
}
