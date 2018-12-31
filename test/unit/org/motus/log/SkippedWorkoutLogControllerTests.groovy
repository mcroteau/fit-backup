package org.motus.log



import org.junit.*
import grails.test.mixin.*

@TestFor(SkippedWorkoutLogController)
@Mock(SkippedWorkoutLog)
class SkippedWorkoutLogControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/skippedWorkoutLog/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.skippedWorkoutLogInstanceList.size() == 0
        assert model.skippedWorkoutLogInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.skippedWorkoutLogInstance != null
    }

    void testSave() {
        controller.save()

        assert model.skippedWorkoutLogInstance != null
        assert view == '/skippedWorkoutLog/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/skippedWorkoutLog/show/1'
        assert controller.flash.message != null
        assert SkippedWorkoutLog.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/skippedWorkoutLog/list'

        populateValidParams(params)
        def skippedWorkoutLog = new SkippedWorkoutLog(params)

        assert skippedWorkoutLog.save() != null

        params.id = skippedWorkoutLog.id

        def model = controller.show()

        assert model.skippedWorkoutLogInstance == skippedWorkoutLog
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/skippedWorkoutLog/list'

        populateValidParams(params)
        def skippedWorkoutLog = new SkippedWorkoutLog(params)

        assert skippedWorkoutLog.save() != null

        params.id = skippedWorkoutLog.id

        def model = controller.edit()

        assert model.skippedWorkoutLogInstance == skippedWorkoutLog
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/skippedWorkoutLog/list'

        response.reset()

        populateValidParams(params)
        def skippedWorkoutLog = new SkippedWorkoutLog(params)

        assert skippedWorkoutLog.save() != null

        // test invalid parameters in update
        params.id = skippedWorkoutLog.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/skippedWorkoutLog/edit"
        assert model.skippedWorkoutLogInstance != null

        skippedWorkoutLog.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/skippedWorkoutLog/show/$skippedWorkoutLog.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        skippedWorkoutLog.clearErrors()

        populateValidParams(params)
        params.id = skippedWorkoutLog.id
        params.version = -1
        controller.update()

        assert view == "/skippedWorkoutLog/edit"
        assert model.skippedWorkoutLogInstance != null
        assert model.skippedWorkoutLogInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/skippedWorkoutLog/list'

        response.reset()

        populateValidParams(params)
        def skippedWorkoutLog = new SkippedWorkoutLog(params)

        assert skippedWorkoutLog.save() != null
        assert SkippedWorkoutLog.count() == 1

        params.id = skippedWorkoutLog.id

        controller.delete()

        assert SkippedWorkoutLog.count() == 0
        assert SkippedWorkoutLog.get(skippedWorkoutLog.id) == null
        assert response.redirectedUrl == '/skippedWorkoutLog/list'
    }
}
