(function () {
	'use strict';

	angular
		.module('app')
		.directive('pageTitle', pageTitle);


	function pageTitle($rootScope, $timeout) {
		return {
			link: function (scope, element) {
				var listener = function (event, toState, toParams, fromState, fromParams) {
					var title = 'Control';
					// Create your own title pattern
					if (toState.data && toState.data.pageTitle) {
						title = toState.data.pageTitle + ' | ' + title;
					}
					$timeout(function () {
						element.text(title);
					});
				};
				$rootScope.$on('$stateChangeStart', listener);
			}
		};
	}


})();
