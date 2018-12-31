import org.apache.shiro.crypto.hash.Sha512Hash
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.crypto.hash.Sha1Hash

import org.motus.Account
import org.motus.Role
import org.motus.common.RoleName
import org.motus.WorkoutPlan
import org.motus.Competition
import org.motus.CompetitionInvite
import org.motus.common.CompetitionStatus
import org.motus.common.CompetitionInviteStatus


class BootStrap {

	def pass
	def adminRole
	def simpleRole
	def grailsApplication
	def workoutPlanService

	def workoutPlanOptionService
	
    def init = { servletContext ->
		setupTestEnvironment()
	}
	
	def setupTestEnvironment(){
		createRoles()
		createUsers()
		createWorkoutPlan()
		createCompetition()
		printValues()
	}
	
    def destroy = {}

	def createRoles = {
		if(Role.count() == 0){
			adminRole = new Role(name : RoleName.ROLE_ADMIN.description()).save(flush:true)
			simpleRole = new Role(name : RoleName.ROLE_SIMPLE.description()).save(flush:true)
		}else{
			adminRole = Role.findByName(RoleName.ROLE_ADMIN.description())
			simpleRole = Role.findByName(RoleName.ROLE_SIMPLE.description())
		}
	}
	
	
	def createUsers = {
		
		//if(Account.count() == 0){
		
			pass = new Sha256Hash('motus').toHex()
			
			def me = new Account(username : 'motus', passwordHash : pass, name : 'motus', email : 'admin@motus.com')
			me.addToRoles(simpleRole)
			me.addToRoles(adminRole)
			
			simpleRole.addToAccounts(me)
			adminRole.addToAccounts(me)
			
			simpleRole.save(flush:true)
			adminRole.save(flush:true)
			
			me.save(flush:true)
			
			
			def followerPass = new Sha256Hash('lead').toHex()
			
			def follow1 = new Account(username : 'follow1', passwordHash : followerPass, name : 'follow1', email : 'follow1@motus.com')
			follow1.addToRoles(simpleRole)
			follow1.save(flush:true)
			
			def follow2 = new Account(username : 'follow2', passwordHash : followerPass, name : 'follow2', email : 'follow2@motus.com')
			follow2.addToRoles(simpleRole)
			follow2.save(flush:true)
			
			simpleRole.addToAccounts(follow1)
			simpleRole.addToAccounts(follow2)
			
			simpleRole.save(flushe:true)
			
					
		//}
		
	}
	
	
	def createWorkoutPlan(){
		
		def plan = workoutPlanOptionService.getWorkoutPlan('Classic')
		def account = Account.findByUsername('motus')
		
		def startDate = new Date()
		
		workoutPlanService.createWorkoutPlan(account, plan, startDate)
		
	}
	
	
	
	def createCompetition(){		
	
		def plan = workoutPlanOptionService.getWorkoutPlan('Classic')
		
		def startDate = new Date()
		
		def organizer = Account.findByUsername('motus')
		def organizerWorkoutPlan = workoutPlanService.createWorkoutPlan(organizer, plan, startDate)
		
		def competition = new Competition()
		
		competition.startDate = startDate
		competition.endDate = organizerWorkoutPlan.plannedCompleteDate
		competition.program = plan.name
		competition.description = plan.description
		competition.status = CompetitionStatus.ACTIVE.description()
		
		competition.addToWorkoutPlans(organizerWorkoutPlan)
		competition.addToMembers(organizer)
		competition.save(flush:true)

		
		organizerWorkoutPlan.competition = competition
		organizerWorkoutPlan.save(flush:true)
		
		
		organizer.addToPermissions("competition:edit:${competition.id}")
		organizer.save(flush:true)
		
	
		def member1 = Account.findByUsername('follow1')
		def member1WorkoutPlan = workoutPlanService.createWorkoutPlan(member1, plan, startDate)
		member1WorkoutPlan.competition = competition
		member1WorkoutPlan.save(flush:true)
		
		
		competition.addToMembers(member1)
		competition.addToWorkoutPlans(member1WorkoutPlan)
		competition.save(flush:true)
		

	
		def invite1 = hydrateInvite(member1, competition, new Date(), true)
		invite1.save(flush:true)
		
		
		def member2 = Account.findByUsername('follow2')
		def invite2 = hydrateInvite(member2, competition, new Date(), false)
		invite2.save(flush:true)
		
				
	}
	
	
	def printValues(){
	
		println "\n Roles : ${Role.count()}"
		println "Accounts : ${Account.count()}"
		println "WorkoutPlans : ${WorkoutPlan.count()}"
		println "Competitions : ${Competition.count()}"
		println "CompetitionInvites : ${CompetitionInvite.count()}\n"
	
	}
	
	def hydrateInvite(account, competition, date, accepted){
		
		def invite = new CompetitionInvite()
		
		invite.status = CompetitionInviteStatus.PENDING.description()
		invite.dateInvited = date
		
		invite.account = account
		invite.competition = competition	
		invite.dateCreated = new Date()
		invite.dateUpdated = new Date()
		
		def dateAcknowledged = new Date() + 2
		invite.dateAcknowledged = dateAcknowledged
		
		if(accepted){
			invite.dateAccepted = dateAcknowledged
		}else{
			invite.dateIgnored = dateAcknowledged
		}
		println "INVITE : ${invite}"
		return invite
	}
	
	
	
	def createWorkoutPlan(account){
		def workoutPlan = new WorkoutPlan()
		
		workoutPlan.account = account
		workoutPlan.startDate = new Date()
		
	}
	
	
	
}
