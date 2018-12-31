package org.motus.workouts



import org.junit.*
import grails.test.mixin.*

@TestFor(BackBicepsController)
@Mock(BackBiceps)
class BackBicepsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/backBiceps/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.backBicepsInstanceList.size() == 0
        assert model.backBicepsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.backBicepsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.backBicepsInstance != null
        assert view == '/backBiceps/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/backBiceps/show/1'
        assert controller.flash.message != null
        assert BackBiceps.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/backBiceps/list'

        populateValidParams(params)
        def backBiceps = new BackBiceps(params)

        assert backBiceps.save() != null

        params.id = backBiceps.id

        def model = controller.show()

        assert model.backBicepsInstance == backBiceps
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/backBiceps/list'

        populateValidParams(params)
        def backBiceps = new BackBiceps(params)

        assert backBiceps.save() != null

        params.id = backBiceps.id

        def model = controller.edit()

        assert model.backBicepsInstance == backBiceps
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/backBiceps/list'

        response.reset()

        populateValidParams(params)
        def backBiceps = new BackBiceps(params)

        assert backBiceps.save() != null

        // test invalid parameters in update
        params.id = backBiceps.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/backBiceps/edit"
        assert model.backBicepsInstance != null

        backBiceps.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/backBiceps/show/$backBiceps.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        backBiceps.clearErrors()

        populateValidParams(params)
        params.id = backBiceps.id
        params.version = -1
        controller.update()

        assert view == "/backBiceps/edit"
        assert model.backBicepsInstance != null
        assert model.backBicepsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/backBiceps/list'

        response.reset()

        populateValidParams(params)
        def backBiceps = new BackBiceps(params)

        assert backBiceps.save() != null
        assert BackBiceps.count() == 1

        params.id = backBiceps.id

        controller.delete()

        assert BackBiceps.count() == 0
        assert BackBiceps.get(backBiceps.id) == null
        assert response.redirectedUrl == '/backBiceps/list'
    }
}
