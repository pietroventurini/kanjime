'use strict'

angular.module('kanjiApp')
    .directive('kanjiPreview', function() {
        return {
            restrict: 'E',
            controller: 'KanjiPreviewCtrl',
            templateUrl: 'views/details.preview.html'
        }
    });