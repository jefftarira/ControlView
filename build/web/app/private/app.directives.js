(function () {
	'use strict';

	angular
		.module('app')
		.directive('minimalizaSidebar', minimalizaSidebar)
		.directive('truncate', truncate)
		.directive('pageTitle', pageTitle)
		.directive('icheck', icheck)
		.directive('ngFileModel', ngFileModel)
		.directive('iboxTools', iboxTools);


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

	function iboxTools($timeout) {
		return {
			restrict: 'A',
			scope: true,
			templateUrl: 'views/common/ibox_tools.html',
			controller: function ($scope, $element) {
				// Function for collapse ibox
				$scope.showhide = function () {
					var ibox = $element.closest('div.ibox');
					var icon = $element.find('i:first');
					var content = ibox.find('div.ibox-content');
					content.slideToggle(200);
					// Toggle icon from up to down
					icon.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
					ibox.toggleClass('').toggleClass('border-bottom');
					$timeout(function () {
						ibox.resize();
						ibox.find('[id^=map-]').resize();
					}, 50);
				};
				// Function for close ibox
				$scope.closebox = function () {
					var ibox = $element.closest('div.ibox');
					ibox.remove();
				}
			}
		};
	}


	function minimalizaSidebar($timeout) {
		return {
			restrict: 'A',
			template: '<a class="navbar-minimalize minimalize-styl-2 btn btn-success " href="" ng-click="minimalize()"><i class="md-icon md-20 md-light">menu</i></a>',
			controller: function ($scope, $element) {
				$scope.minimalize = function () {
					$("body").toggleClass("mini-navbar");
					if (!$('body').hasClass('mini-navbar') || $('body').hasClass(
						'body-small')) {
						// Hide menu in order to smoothly turn on when maximize menu
						$('#side-menu').hide();
						// For smoothly turn on menu
						setTimeout(
							function () {
								$('#side-menu').fadeIn(400);
							}, 200);
					} else if ($('body').hasClass('fixed-sidebar')) {
						$('#side-menu').hide();
						setTimeout(
							function () {
								$('#side-menu').fadeIn(400);
							}, 100);
					} else {
						// Remove all inline style from jquery fadeIn function to reset menu state
						$('#side-menu').removeAttr('style');
					}
				};
			}
		};
	}

	/**
	 * truncate - Directive for truncate string
	 */
	function truncate($timeout) {
		return {
			restrict: 'A',
			scope: {
				truncateOptions: '='
			},
			link: function (scope, element) {
				$timeout(function () {
					element.dotdotdot(scope.truncateOptions);

				});
			}
		};
	}

	function icheck($timeout) {
		return {
			restrict: 'A',
			require: 'ngModel',
			link: function ($scope, element, $attrs, ngModel) {
				return $timeout(function () {
					var value;
					value = $attrs['value'];

					$scope.$watch($attrs['ngModel'], function (newValue) {
						$(element).iCheck('update');
					})

					return $(element).iCheck({
						checkboxClass: 'icheckbox_square-green',
						radioClass: 'iradio_square-green'

					}).on('ifChanged', function (event) {
						if ($(element).attr('type') === 'checkbox' && $attrs['ngModel']) {
							$scope.$apply(function () {
								return ngModel.$setViewValue(event.target.checked);
							});
						}
						if ($(element).attr('type') === 'radio' && $attrs['ngModel']) {
							return $scope.$apply(function () {
								return ngModel.$setViewValue(value);
							});
						}
					});
				});
			}
		};
	}

	function ngFileModel() {
		return {
			scope: {
				ngFileModel: "="
			},
			link: function (scope, element, attributes) {
				element.bind("change", function (changeEvent) {
					var readers = [],
						files = changeEvent.target.files,
						datas = [];
					for (var i = 0; i < files.length; i++) {

						if (files[i].size > 4000000) continue;

						readers[i] = new FileReader();
						readers[i].index = i;
						readers[i].onload = function (loadEvent) {
							var index = loadEvent.target.index;
							scope.$apply(function () {
								var dataFile = new Blob([loadEvent.target.result], {
									type: files[index].type,
								});
								scope.ngFileModel.push({
									id: 0,
									lastModified: files[index].lastModified,
									lastModifiedDate: files[index].lastModifiedDate,
									nombre: files[index].name,
									size: files[index].size,
									shortSize: bytesToSize(files[index].size),
									tipo: files[index].type,
									data: loadEvent.target.result,
									fileUrl: URL.createObjectURL(dataFile)
								});
							});
						};
						readers[i].readAsDataURL(files[i]);
					}
				});
			}
		};
	}

	function bytesToSize(bytes) {
		var sizes = ['B', 'Kb', 'Mb', 'Gb', 'Tb', 'Pb'];
		for (var i = 0; i < sizes.length; i++) {
			if (bytes <= 1024) {
				return bytes + ' ' + sizes[i];
			} else {
				bytes = parseFloat(bytes / 1024).toFixed(2);
			}
		}
		return bytes + ' P';
	}


})();
