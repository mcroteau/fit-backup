package org.motus



import org.junit.*
import grails.test.mixin.*

@TestFor(CompetitionMemberController)
@Mock(CompetitionMember)
class CompetitionMemberControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/competitionMember/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.competitionMemberInstanceList.size() == 0
        assert model.competitionMemberInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.competitionMemberInstance != null
    }

    void testSave() {
        controller.save()

        assert model.competitionMemberInstance != null
        assert view == '/competitionMember/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/competitionMember/show/1'
        assert controller.flash.message != null
        assert CompetitionMember.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/competitionMember/list'

        populateValidParams(params)
        def competitionMember = new CompetitionMember(params)

        assert competitionMember.save() != null

        params.id = competitionMember.id

        def model = controller.show()

        assert model.competitionMemberInstance == competitionMember
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/competitionMember/list'

        populateValidParams(params)
        def competitionMember = new CompetitionMember(params)

        assert competitionMember.save() != null

        params.id = competitionMember.id

        def model = controller.edit()

        assert model.competitionMemberInstance == competitionMember
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/competitionMember/list'

        response.reset()

        populateValidParams(params)
        def competitionMember = new CompetitionMember(params)

        assert competitionMember.save() != null

        // test invalid parameters in update
        params.id = competitionMember.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/competitionMember/edit"
        assert model.competitionMemberInstance != null

        competitionMember.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/competitionMember/show/$competitionMember.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        competitionMember.clearErrors()

        populateValidParams(params)
        params.id = competitionMember.id
        params.version = -1
        controller.update()

        assert view == "/competitionMember/edit"
        assert model.competitionMemberInstance != null
        assert model.competitionMemberInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/competitionMember/list'

        response.reset()

        populateValidParams(params)
        def competitionMember = new CompetitionMember(params)

        assert competitionMember.save() != null
        assert CompetitionMember.count() == 1

        params.id = competitionMember.id

        controller.delete()

        assert CompetitionMember.count() == 0
        assert CompetitionMember.get(competitionMember.id) == null
        assert response.redirectedUrl == '/competitionMember/list'
    }
}
