package org.motus



import org.junit.*
import grails.test.mixin.*

@TestFor(CompetitionController)
@Mock(Competition)
class CompetitionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/competition/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.competitionInstanceList.size() == 0
        assert model.competitionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.competitionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.competitionInstance != null
        assert view == '/competition/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/competition/show/1'
        assert controller.flash.message != null
        assert Competition.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/competition/list'

        populateValidParams(params)
        def competition = new Competition(params)

        assert competition.save() != null

        params.id = competition.id

        def model = controller.show()

        assert model.competitionInstance == competition
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/competition/list'

        populateValidParams(params)
        def competition = new Competition(params)

        assert competition.save() != null

        params.id = competition.id

        def model = controller.edit()

        assert model.competitionInstance == competition
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/competition/list'

        response.reset()

        populateValidParams(params)
        def competition = new Competition(params)

        assert competition.save() != null

        // test invalid parameters in update
        params.id = competition.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/competition/edit"
        assert model.competitionInstance != null

        competition.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/competition/show/$competition.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        competition.clearErrors()

        populateValidParams(params)
        params.id = competition.id
        params.version = -1
        controller.update()

        assert view == "/competition/edit"
        assert model.competitionInstance != null
        assert model.competitionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/competition/list'

        response.reset()

        populateValidParams(params)
        def competition = new Competition(params)

        assert competition.save() != null
        assert Competition.count() == 1

        params.id = competition.id

        controller.delete()

        assert Competition.count() == 0
        assert Competition.get(competition.id) == null
        assert response.redirectedUrl == '/competition/list'
    }
}
