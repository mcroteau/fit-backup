package org.motus

import org.springframework.dao.DataIntegrityViolationException

class CompetitionMemberController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [competitionMemberInstanceList: CompetitionMember.list(params), competitionMemberInstanceTotal: CompetitionMember.count()]
    }

    def create() {
        [competitionMemberInstance: new CompetitionMember(params)]
    }

    def save() {
        def competitionMemberInstance = new CompetitionMember(params)
        if (!competitionMemberInstance.save(flush: true)) {
            render(view: "create", model: [competitionMemberInstance: competitionMemberInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), competitionMemberInstance.id])
        redirect(action: "show", id: competitionMemberInstance.id)
    }

    def show(Long id) {
        def competitionMemberInstance = CompetitionMember.get(id)
        if (!competitionMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "list")
            return
        }

        [competitionMemberInstance: competitionMemberInstance]
    }

    def edit(Long id) {
        def competitionMemberInstance = CompetitionMember.get(id)
        if (!competitionMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "list")
            return
        }

        [competitionMemberInstance: competitionMemberInstance]
    }

    def update(Long id, Long version) {
        def competitionMemberInstance = CompetitionMember.get(id)
        if (!competitionMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (competitionMemberInstance.version > version) {
                competitionMemberInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'competitionMember.label', default: 'CompetitionMember')] as Object[],
                          "Another user has updated this CompetitionMember while you were editing")
                render(view: "edit", model: [competitionMemberInstance: competitionMemberInstance])
                return
            }
        }

        competitionMemberInstance.properties = params

        if (!competitionMemberInstance.save(flush: true)) {
            render(view: "edit", model: [competitionMemberInstance: competitionMemberInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), competitionMemberInstance.id])
        redirect(action: "show", id: competitionMemberInstance.id)
    }

    def delete(Long id) {
        def competitionMemberInstance = CompetitionMember.get(id)
        if (!competitionMemberInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "list")
            return
        }

        try {
            competitionMemberInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'competitionMember.label', default: 'CompetitionMember'), id])
            redirect(action: "show", id: id)
        }
    }
}
