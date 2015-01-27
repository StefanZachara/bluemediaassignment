$(document).ready(function() {
	$('.gatherer').each(function() {
		var cardName = $(this).text();
		$(this).qtip({
			content: $('<img />').attr('src', 'http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=' + cardName),
			position: {
				my: 'right center',
				at: 'left center'
			},
			style: {
				classes: 'ui-tooltip qtip ui-tooltip-default ui-tooltip-shadow ui-tooltip-light ui-tooltip-rounded'
			}
		});
	});
});
