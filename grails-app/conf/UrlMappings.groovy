class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/" {
		    controller = "content"
		    action = "home"
		}
		
		"/account/public/$username?" {
			controller = "account"
			action = "info"
		}
		
		"500"(view:'/error')
	}
}
