'use strict';

angular
  .module('kanjiApp', [
    'ui.router',
    'kanjiControllers',
    'kanjiServices',
    'ngAnimate',
    'ngAria',
    'ngMaterial'
  ])

.config(
  function($stateProvider, $urlRouterProvider) {
    
    $stateProvider
      .state('root', {
        abstract: true,
        views: {
          '': {
            templateUrl: 'views/layout.html'
          },
          'toolbar@root': {
            templateUrl: 'views/toolbar.html',
            controller: 'KanjiFinderCtrl'
          },
          'content@root': {
            templateUrl: 'views/content.html',
            controller: function($scope){}
          }
        }
      })
      
      .state('root.search', {
        parent: 'root',
        url: '/search'
      })
      
      .state('root.search.result', {
        parent: 'root.search',
        url: '/:keyword',
        views: {
          'content@root': {
            templateUrl: 'views/result.html',
            controller: 'KanjiResultCtrl'
          }
        }
      })
      
      .state('root.search.result.details', {
        parent: 'root.search.result',
        url: '/:character',
        views: {
          'content@root': {
            templateUrl: 'views/details.html',
            controller: 'KanjiDetailsCtrl'
          }
        }
      })
      
      $urlRouterProvider.otherwise('/search');
  })
  
  .config(function($mdThemingProvider) {
    $mdThemingProvider.theme('default')
      .primaryPalette('e', {
      'default': '400', // by default use shade 400 from the pink palette for primary intentions
      'hue-1': '100', // use shade 100 for the <code>md-hue-1</code> class
      'hue-2': '600', // use shade 600 for the <code>md-hue-2</code> class
      'hue-3': 'A100' // use shade A100 for the <code>md-hue-3</code> class
    })
      .accentPalette('pink', {
      'default': '400', // by default use shade 400 from the pink palette for primary intentions
      'hue-1': '100', // use shade 100 for the <code>md-hue-1</code> class
      'hue-2': '600', // use shade 600 for the <code>md-hue-2</code> class
      'hue-3': 'A100' // use shade A100 for the <code>md-hue-3</code> class
    });
  });
