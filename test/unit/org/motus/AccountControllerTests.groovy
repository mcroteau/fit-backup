package org.motus

import grails.test.mixin.*
import org.junit.*
import groovy.mock.interceptor.MockFor

@TestFor(AccountController)
class AccountControllerTests extends GroovyTestCase {

	def controller
	def accountService 
	
	void setup(){
		println "BEFORE EACH TEST"
		controller = new AccountController()
		accountService = new MockFor(AccountService)
		accountService.use{
			controller.accountService = new AccountService()
		}
	}
    
	void testRegister() {
		println "REGISTER CALL"
    }

}