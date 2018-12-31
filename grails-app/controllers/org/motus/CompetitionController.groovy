package org.motus

import org.springframework.dao.DataIntegrityViolationException

class CompetitionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [competitionInstanceList: Competition.list(params), competitionInstanceTotal: Competition.count()]
    }

    def create() {
        [competitionInstance: new Competition(params)]
    }

    def save() {
        def competitionInstance = new Competition(params)
        if (!competitionInstance.save(flush: true)) {
            render(view: "create", model: [competitionInstance: competitionInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'competition.label', default: 'Competition'), competitionInstance.id])
        redirect(action: "show", id: competitionInstance.id)
    }

    def show(Long id) {
        def competitionInstance = Competition.get(id)
        if (!competitionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "list")
            return
        }

        [competitionInstance: competitionInstance]
    }

    def edit(Long id) {
        def competitionInstance = Competition.get(id)
        if (!competitionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "list")
            return
        }

        [competitionInstance: competitionInstance]
    }

    def update(Long id, Long version) {
        def competitionInstance = Competition.get(id)
        if (!competitionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (competitionInstance.version > version) {
                competitionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'competition.label', default: 'Competition')] as Object[],
                          "Another user has updated this Competition while you were editing")
                render(view: "edit", model: [competitionInstance: competitionInstance])
                return
            }
        }

        competitionInstance.properties = params

        if (!competitionInstance.save(flush: true)) {
            render(view: "edit", model: [competitionInstance: competitionInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'competition.label', default: 'Competition'), competitionInstance.id])
        redirect(action: "show", id: competitionInstance.id)
    }

    def delete(Long id) {
        def competitionInstance = Competition.get(id)
        if (!competitionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "list")
            return
        }

        try {
            competitionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'competition.label', default: 'Competition'), id])
            redirect(action: "show", id: id)
        }
    }
}
