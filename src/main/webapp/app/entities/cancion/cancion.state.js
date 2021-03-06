(function() {
    'use strict';

    angular
        .module('appApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cancion', {
            parent: 'entity',
            url: '/cancion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.cancion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cancion/cancions.html',
                    controller: 'CancionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cancion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cancion-detail', {
            parent: 'cancion',
            url: '/cancion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'appApp.cancion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cancion/cancion-detail.html',
                    controller: 'CancionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cancion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cancion', function($stateParams, Cancion) {
                    return Cancion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cancion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cancion-detail.edit', {
            parent: 'cancion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cancion/cancion-dialog.html',
                    controller: 'CancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cancion', function(Cancion) {
                            return Cancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cancion.new', {
            parent: 'cancion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cancion/cancion-dialog.html',
                    controller: 'CancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                duracion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cancion', null, { reload: 'cancion' });
                }, function() {
                    $state.go('cancion');
                });
            }]
        })
        .state('cancion.edit', {
            parent: 'cancion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cancion/cancion-dialog.html',
                    controller: 'CancionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cancion', function(Cancion) {
                            return Cancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cancion', null, { reload: 'cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cancion.delete', {
            parent: 'cancion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cancion/cancion-delete-dialog.html',
                    controller: 'CancionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cancion', function(Cancion) {
                            return Cancion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cancion', null, { reload: 'cancion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
