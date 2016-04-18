(function() {
    
    angular.module('kanjiControllers', [])
        .controller('KanjiFinderCtrl', ['$scope', 'Kanji', '$state', '$stateParams', '$filter', function($scope, Kanji, $state, $stateParams, $filter) {
            $scope.searchKanji = function(keyword) {
                $state.go('root.search.result', { keyword: keyword });
            };
            $scope.reset = function() {
                    $scope.keyword = "";
                };
        }])
        
        .controller('KanjiResultCtrl', ['$scope', 'Kanji', '$state', '$stateParams', function($scope, Kanji, $state, $stateParams) {
            $scope.keyword = $stateParams.keyword;
            Kanji.search($scope.keyword).then(function(result) {
                $scope.words = result;
            });
            
            $scope.getDetails = function(character) {
                $state.go('root.search.result.details', { character: character });
            };
        }])
        
        .controller('KanjiDetailsCtrl', ['$scope', '$state', 'Kanji', '$stateParams', function($scope, $state, Kanji, $stateParams) {
            $scope.character = $stateParams.character;
            
            Kanji.getDetails($scope.character).then(function(result) {
                $scope.details = result;
                $scope.kanjiImages = $scope.details.kanji.strokes.images;
                $scope.index = $scope.details.kanji.strokes.images.length-1;
                $scope.kanjiImage = $scope.kanjiImages[$scope.index];
            });
        }])
        
        .controller('KanjiPreviewCtrl', ['$scope', function($scope) {
            $scope.next = function() {
                if($scope.index >= $scope.details.kanji.strokes.images.length-1){
                    $scope.index = 0;
                }
                else {
                    $scope.index++;
                }
                $scope.kanjiImage = $scope.kanjiImages[$scope.index];
            }
            
            $scope.previous = function() {
                if($scope.index <= 0){
                    $scope.index = $scope.details.kanji.strokes.images.length-1;
                }
                else {
                    $scope.index--;
                }
                $scope.kanjiImage = $scope.kanjiImages[$scope.index];
            }
            
            $scope.play = function() {
                $scope.kanjiVideo = $scope.details.kanji.video.webm;
            }
        }]);
})();