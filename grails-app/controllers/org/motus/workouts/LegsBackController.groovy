package org.motus.workouts

import org.springframework.dao.DataIntegrityViolationException
import org.apache.shiro.SecurityUtils
import org.motus.log.SkippedWorkoutLog
import org.motus.Account
import org.motus.BaseController
import org.motus.AppConstants

@Mixin(BaseController)
class LegsBackController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def index() {
        redirect(action: "list", params: params)
    }


	def entry(Long id){
		withPlannedWorkout { legsBackInstance ->
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.ENTRY_VIEW)
		}
	}
	
	
	def changeDate(Long id){
		withPlannedWorkout { legsBackInstance ->
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.CHANGE_DATE_VIEW)
		}
	}

    def list(Integer max) {
		authenticated { subject -> 
       		params.max = Math.min(max ?: 10, 100)
        	[legsBackInstanceList : LegsBack.list(params), legsBackInstanceTotal: CLASS_NAME.count()]
		}
    }


    def show(Long id) {
		withPlannedWorkout { legsBackInstance ->
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.SHOW_VIEW)
		}
    }


	def confirmSkip(Long id){
		withPlannedWorkout { legsBackInstance ->
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.CONFIRM_SKIP_VIEW)
		}
	}
	
	
	def skip(Long id){
		withPlannedWorkoutSkipped { legsBackInstance ->
			flash.message = AppConstants.SKIP_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}

	
	def confirmRecover(Long id){
		withPlannedWorkout { legsBackInstance ->
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.CONFIRM_RECOVER_VIEW)
		}
	}
	
	
	def recover(Long id){
		withPlannedWorkoutRecovered { legsBackInstance ->
			flash.message = AppConstants.RECOVERY_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}
	

    def update(Long id, Long version) {
		withPlannedWorkoutUpdated { legsBackInstance ->
			flash.message = AppConstants.UPDATE_SUCCESS_MESSAGE
			request.plannedWorkout = legsBackInstance
			render(view : AppConstants.SHOW_VIEW)
		}
	}
	
}