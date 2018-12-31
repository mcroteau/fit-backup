package org.motus

import org.springframework.dao.DataIntegrityViolationException

class AccountNotificationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [accountNotificationInstanceList: AccountNotification.list(params), accountNotificationInstanceTotal: AccountNotification.count()]
    }

    def create() {
        [accountNotificationInstance: new AccountNotification(params)]
    }

    def save() {
        def accountNotificationInstance = new AccountNotification(params)
        if (!accountNotificationInstance.save(flush: true)) {
            render(view: "create", model: [accountNotificationInstance: accountNotificationInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), accountNotificationInstance.id])
        redirect(action: "show", id: accountNotificationInstance.id)
    }

    def show(Long id) {
        def accountNotificationInstance = AccountNotification.get(id)
        if (!accountNotificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "list")
            return
        }

        [accountNotificationInstance: accountNotificationInstance]
    }

    def edit(Long id) {
        def accountNotificationInstance = AccountNotification.get(id)
        if (!accountNotificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "list")
            return
        }

        [accountNotificationInstance: accountNotificationInstance]
    }

    def update(Long id, Long version) {
        def accountNotificationInstance = AccountNotification.get(id)
        if (!accountNotificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (accountNotificationInstance.version > version) {
                accountNotificationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'accountNotification.label', default: 'AccountNotification')] as Object[],
                          "Another user has updated this AccountNotification while you were editing")
                render(view: "edit", model: [accountNotificationInstance: accountNotificationInstance])
                return
            }
        }

        accountNotificationInstance.properties = params

        if (!accountNotificationInstance.save(flush: true)) {
            render(view: "edit", model: [accountNotificationInstance: accountNotificationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), accountNotificationInstance.id])
        redirect(action: "show", id: accountNotificationInstance.id)
    }

    def delete(Long id) {
        def accountNotificationInstance = AccountNotification.get(id)
        if (!accountNotificationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "list")
            return
        }

        try {
            accountNotificationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'accountNotification.label', default: 'AccountNotification'), id])
            redirect(action: "show", id: id)
        }
    }
}
