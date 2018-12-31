package org.motus

import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject

class SecurityFilters {

    def filters = {
		roleMaintenance(controller: "role", action: "(create|edit|save|update|delete|list)") {
		    before = {
		        accessControl {
		            role("ROLE_ADMIN")
		        }
		    }
		}
    }
}
