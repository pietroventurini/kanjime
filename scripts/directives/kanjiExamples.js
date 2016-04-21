'use strict'

angular.module('kanjiApp')
    .directive('kanjiExamples', function() {
        return {
            restrict: 'E',
            controller: 'KanjiExamplesCtrl',
            templateUrl: 'views/details.examples.html'
        }
    });