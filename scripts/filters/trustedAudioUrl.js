'use strict'

angular.module('kanjiApp')
    .filter('trustedAudioUrl', function($sce) {
        return function(audioFile) {
            return $sce.trustAsResourceUrl(audioFile);
        };
    })