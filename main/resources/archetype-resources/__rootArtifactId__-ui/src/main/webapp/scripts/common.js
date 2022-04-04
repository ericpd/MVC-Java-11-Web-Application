/** * START - Toggle Switch ** */
function toggleSwitch($toggle) {
	if($toggle.is("[disabled]")) {
		return false;
	}
	
    $toggle.find('.btn').toggleClass('active');  
    
    $toggle.find('.btn').removeClass('btn-success');
    $toggle.find('.btn').removeClass('btn-warning');
    
    $toggle.find('.btn').toggleClass('btn-default');
    
    var $newActive = $toggle.find('.btn.active');
    var $activeValue = $newActive.data('publish');
    if($activeValue) { // publish selected
    	$newActive.addClass('btn-success');
    } else { // unpublish selected
    	$newActive.addClass('btn-warning');
    }
}
/** * END - Toggle Switch** */

/** * START - Custom Image Picker ** */
(function ($) {
	 
    $.fn.imagePicker = function(options) {
        
        // Create an input inside each matched element
        return this.each(function() {
            
            // Define plugin options
            var settings = $.extend({
                // Input name attribute
                name: $(this).data('name'),
                // Input value attribute
                value: $(this).data('value'),
                // Classes for styling the input
                class: "form-control btn btn-default btn-block",
                // Icon which displays in center of input
                icon: "glyphicon glyphicon-plus"
            }, options);
            
        	if(undefined == settings.value || '' == settings.value) {
        		$(this).html(createBtn(this, settings));
        	} else {
        		$(this).html(createPreview(this, settings.value, settings));
        	}
        });
 
    };
 
    // Private function for creating the input element
    function createBtn(that, settings) {
    	var maxImageSize = 500000;
        // The input icon element
        var pickerBtnIcon = $('<img src="images/user-large.png?<xxxtest:currentDate />" />');
        
        // The actual file input which stays hidden
        var pickerBtnInput = $('<input type="file" name="' + settings.name + '" accept="image/*" />');
        
        // The actual element displayed
        var pickerBtn = $('<div class="' + settings.class + ' img-upload-btn"></div>')
            .append(pickerBtnIcon)
            .append(pickerBtnInput);
            
        // File load listener
        pickerBtnInput.change(function() {
            if ($(this).prop('files')[0]) {
                // Use FileReader to get file
                var reader = new FileReader();
                if($(this).prop('files')[0].size > maxImageSize) {
                	 showErrorMessage('Image file size exceeds 500KB.');
                	 return;
                }
               
                // Create a preview once image has loaded
                reader.onload = function(e) {
                    var preview = createPreview(that, e.target.result, settings);
                    $(that).html(preview);
                }
                
                // Load image
                reader.readAsDataURL(pickerBtnInput.prop('files')[0]);
            }                
        });

        return pickerBtn
    };
    
    // Private function for creating a preview element
    function createPreview(that, src, settings) {
        
            // The preview image
            var pickerPreviewImage = $('<img src="' + src + '" class="img-responsive img-rounded" width="100%"/>');
            
            // The remove image button
            var pickerPreviewRemove = $('<a class="btn btn-warning btn-block img-pick-remove">Remove</a>');
            
            var pickerHiddenInput = $('<input name="' + settings.name + '" class="form-control" type="hidden" value="' + src + '"/>');
            
            // The preview element
            var pickerPreview = $('<div class="text-center"></div>')
                .append(pickerPreviewImage)
                .append(pickerPreviewRemove)
                .append(pickerHiddenInput);

            // Remove image listener
            pickerPreviewRemove.click(function() {
            	var $button = $(this);  
            	// prevent any action if the button is disabled
            	if($button.attr("disabled") || $button.prop("disabled")) {
        			return false;
        		}
            	event.stopPropagation();
                var btn = createBtn(that, settings);
                $(that).html(btn);
            });
            
            return pickerPreview;
    };
    
}(jQuery));

$(document).ready(function() {
    $('.img-picker').imagePicker();
});
/** * END - Custom Image Picker ** */

/** * START - Custom Modal Controller for Dynamic page loading ** */
(function ($) {
    $.fn.loadModalController = function(options) {        
        return this.each(function() {       	
        	var $button = $(this);     	
        	$button.on('click', function(event) {   
        		
        		// prevent any action if the button is disabled
            	if($button.attr("disabled") || $button.prop("disabled")) {
        			return false;
        		}
        		event.stopPropagation();
        		
            	var targetController = $button.attr('data-controller-target');
            	var targetElementId = $button.attr('data-target-element'); 
        		$.get(targetController, function(data) {
        			var $targetElement = $(targetElementId);
        			$targetElement.html(data);
        			
        			var $modal = $targetElement.find('.modal');
        			$modal.modal('show');        			
        			$modal.on('hidden.bs.modal', function () {
        				$targetElement.empty(); // clear modal
        			})
        		});
        	});
            
        });
        
    };
}(jQuery));

$(document).ready(function() {
	$('.modal-controller').loadModalController();
});
/** * END - Custom Modal Controller for Dynamic page loading ** */

/** * START - Serialize form into a map ** */
(function($){
    $.fn.serializeObject = function(){

        var self = this,
            json = {},
            push_counters = {},
            patterns = {
                "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)\])*$/,
                "key":      /[a-zA-Z0-9_]+|(?=\[\])/g,
                "push":     /^$/,
                "fixed":    /^\d+$/,
                "named":    /^[a-zA-Z0-9_]+$/
            };


        this.build = function(base, key, value){
            base[key] = value;
            return base;
        };

        this.push_counter = function(key){
            if(push_counters[key] === undefined){
                push_counters[key] = 0;
            }
            return push_counters[key]++;
        };

        $.each($(this).serializeArray(), function(){

            // Skip invalid keys
            if(!patterns.validate.test(this.name)){
                return;
            }

            var k,
                keys = this.name.match(patterns.key),
                merge = this.value,
                reverse_key = this.name;

            while((k = keys.pop()) !== undefined){

                // Adjust reverse_key
                reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                // Push
                if(k.match(patterns.push)){
                    merge = self.build([], self.push_counter(reverse_key), merge);
                }

                // Fixed
                else if(k.match(patterns.fixed)){
                    merge = self.build([], k, merge);
                }

                // Named
                else if(k.match(patterns.named)){
                    merge = self.build({}, k, merge);
                }
            }

            json = $.extend(true, json, merge);
        });

        return json;
    };
})(jQuery);
/** * END - Serialize form into a map ** */

/** * START - Show error message ** */
function showMessage(type, message) {
	toastr.remove();
	toastr.options.timeOut = "800";
	toastr.options.closeButton = true;
	
	switch (type) {
	case 1:
		toastr['warn'](message, '' , { positionClass: "toast-top-center" });
		break;
	case 2:
		toastr['danger'](message, '' , { positionClass: "toast-top-center" });
		break;
	case 3:
		toastr['success'](message, '' , { positionClass: "toast-top-center" });
		break;

	default:
		toastr['info'](message, '' , { positionClass: "toast-top-center" });
		break;
	}
}
/** * END - Show error message ** */

/** * START - Show error message ** */
function showErrorMessage(message) {
	toastr.remove();
	toastr.options.timeOut = "800";
	toastr.options.closeButton = true;
	toastr['error'](message, '' , { positionClass: "toast-top-center" });
}
/** * END - Show error message ** */

/** * START - Show Error Response on AJAX request ** */
function showErrorResponse(response) {
	var $jsonResp = JSON.parse(response.responseText);
	toastr.remove();
	toastr.options.timeOut = "800";
	toastr.options.closeButton = true;
	toastr['error']($jsonResp.message, '' , { positionClass: "toast-top-center" });
}
/** * END - Show Error Response on AJAX request ** */

/** * START - Show success information message ** */
function showSuccessMessage(message) {
	toastr.remove();
	toastr.options.timeOut = "1000";
	toastr.options.closeButton = true;
	toastr['success'](message, '' , { positionClass: "toast-top-center" });
}
/** * END - Show success information message ** */

/** * START - Common confirmation modal ** */
function showModal(title, message, yesCallback, noCallBack) {
	var $modal = $("#modalConfirmation");
	
	$modal.find('#modalTitle:first').html(title);
	$modal.find('#modalMessage:first').html(message);
	$modal.show();
	$modal.modal({
		backdrop : 'static',
		keyboard : false
	})
	$modal.find('#btnYes').off('click').click(function(e) {
		yesCallback();
		$modal.modal('hide');
	});
	$modal.find('.cls-modal').off('click').click(function(e) {
		$modal.modal('hide');
		if (noCallBack == null) {
			return false;
		} else {
			noCallBack();
		}
	});
}
/** * END - Common confirmation modal ** */

/** * START - Common password confirmation modal ** */
function confirmPassword(title, yesCallback) {
	var $modal = $("#modalPasswordConfirmation");

	$modal.find('#modalPasswordConfirmationTitle:first').html(title);
	$modal.modal({
		backdrop : 'static',
		keyboard : true
	})
	
	var $form = $modal.find('#confirmPassForm');	
	$form.trigger('reset');
	$form.find('#modalConfirmPassword').removeAttr('aria-invalid');
	$form.find('#modalConfirmPassword-error').hide();
	$form.find('.error').removeClass('error');	
	$form.submit(function(event) {
		event.preventDefault();
	});
	
	function submitConfirmPasswordForm() {
		if($form.valid()) {	
			$.post('password/confirm', { pass : $form.find('#modalConfirmPassword').val() })
			 .done(function(response) {	
					var $jsonResp = JSON.parse(response);
					var $respData = $jsonResp.data;
				
					if ($respData) {
						yesCallback();
					} else {
						$modal.modal('hide');
						showErrorMessage('Invalid password.');
					}
			 })
			 .fail(function(response) {
				$modal.modal('hide');
				showErrorResponse(response);
			 });
		}
	}
		
	$form.find('#confirmPassConfirmBtn').off('click').on('click', submitConfirmPasswordForm);
	$form.find('#confirmPassCancelBtn').off('click').on('click', function() {
		$modal.modal('hide');
		return false;
	});
	
	$form.find('#modalConfirmPassword').off('keyup').on('keyup', function (e) {
	    if (e.key === 'Enter' || e.keyCode === 13) {
	    	submitConfirmPasswordForm();
		    return false;
	    }
	});
	
	$modal.show();
}
/** * END - Common password confirmation modal ** */

/** * START - POST Redirect ** */
var redirect = function(url, method) {
	var form = document.createElement('form');
    form.method = method;
    form.action = url;
    $(document.body).append(form);
    form.submit();
};
/** * END - POST Redirect ** */



/** * START - Initiate TinyMCE rich text edito ** */
function enableRichEditor() {
	tinymce.init({
	    selector: 'textarea.rich-text',
	    menubar: false,
	    plugins: [
	      'advlist autolink lists link image charmap print preview anchor',
	      'searchreplace visualblocks',
	      'insertdatetime table paste'
	    ],
	    toolbar: 
	    	'undo redo | ' +
	    	'bold italic underline | ' +
	    	'alignleft aligncenter alignright alignjustify | ' +
	    	'bullist numlist outdent indent | ' +
	    	'removeformat | ' +
	    	'formatselect | ' +
	    	'backcolor | ' +
	    	'table',
	    content_css: '//www.tiny.cloud/css/codepen.min.css'
	});
}

function disableRichEditor() {
	tinymce.init({
	    selector: 'textarea.rich-text',
	    menubar: false,
	    readonly: 1,
	    plugins: [
	      'advlist autolink lists link image charmap print preview anchor',
	      'searchreplace visualblocks',
	      'insertdatetime table paste'
	    ],
	    toolbar: 
	    	'undo redo | ' +
	    	'bold italic underline | ' +
	    	'alignleft aligncenter alignright alignjustify | ' +
	    	'bullist numlist outdent indent | ' +
	    	'removeformat | ' +
	    	'formatselect | ' +
	    	'backcolor | ' +
	    	'table',
	    content_css: '//www.tiny.cloud/css/codepen.min.css'
	});
}
/** * END - Initiate TinyMCE rich text edito ** */

/* SET TWO DECIMAL INPUT */

function setTwoNumberDecimal(element) {
	element.value = parseFloat(element.value).toFixed(2);
};