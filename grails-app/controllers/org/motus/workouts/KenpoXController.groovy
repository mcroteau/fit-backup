package org.motus.workouts

import org.springframework.dao.DataIntegrityViolationException
import org.apache.shiro.SecurityUtils
import org.motus.log.SkippedWorkoutLog
import org.motus.Account
import org.motus.BaseController
import org.motus.AppConstants

@Mixin(BaseController)
class KenpoXController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


    def index() {
        redirect(action: "list", params: params)
    }


	def entry(Long id){
		withPlannedWorkout { kenpoXInstance ->
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.ENTRY_VIEW)
		}
	}
	
	
	def changeDate(Long id){
		withPlannedWorkout { kenpoXInstance ->
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.CHANGE_DATE_VIEW)
		}
	}

    def list(Integer max) {
		authenticated { subject -> 
       		params.max = Math.min(max ?: 10, 100)
        	[kenpoXInstanceList: KenpoX.list(params), kenpoXInstanceTotal: KenpoX.count()]
		}
    }


    def show(Long id) {
		withPlannedWorkout { kenpoXInstance ->
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.SHOW_VIEW)
		}
    }


	def confirmSkip(Long id){
		withPlannedWorkout { kenpoXInstance ->
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.CONFIRM_SKIP_VIEW)
		}
	}
	
	
	def skip(Long id){
		withPlannedWorkoutSkipped { kenpoXInstance ->
			flash.message = AppConstants.SKIP_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}

	
	def confirmRecover(Long id){
		withPlannedWorkout { kenpoXInstance ->
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.CONFIRM_RECOVER_VIEW)
		}
	}
	
	
	def recover(Long id){
		withPlannedWorkoutRecovered { kenpoXInstance ->
			flash.message = AppConstants.RECOVERY_SUCCESSFUL_MESSAGE
			redirect(controller:'account', action:'dashboard')
		}
	}
	

    def update(Long id, Long version) {
		withPlannedWorkoutUpdated { kenpoXInstance ->
			flash.message = AppConstants.UPDATE_SUCCESS_MESSAGE
			request.plannedWorkout = kenpoXInstance
			render(view : AppConstants.SHOW_VIEW)
		}
	}
	
}
