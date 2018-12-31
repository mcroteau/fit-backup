package org.motus

import grails.test.mixin.*
import org.junit.*
import org.apache.shiro.crypto.hash.Sha256Hash
import org.motus.common.RoleName
import org.motus.Account
import org.motus.Role

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
@Mock([Account, AccountService])
class AccountServiceTests {
	
	static transactional = true

	def accountService 
	
	void setup(){
		accountService = mockFor(AccountService)
	}
	
	void testRegistration(){

		accountService = new AccountService()
		
		assert Account.count() == 0
		
		def account = new Account()
		account.username = "gratus"
		account.passwordHash = new Sha256Hash("gratus").toHex()
		account.email = "gratus@email.com"
		
		accountService.register(account)
		
		assert Account.count() == 1
		
	}

}
