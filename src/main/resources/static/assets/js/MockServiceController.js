$(document).ready(function() {

    $.notifyDefaults({
    	type: 'minimalist',
    	showProgressbar: true,
        delay: 2000,
        template: '<div data-notify="container" class="col-xs-11 col-sm-3 alert alert-{0}" role="alert">' +
            '<i data-notify="icon"></i>&nbsp;' +
            '<span data-notify="message">{2}</span>' +
            '<div class="progress" data-notify="progressbar">' +
            '<div class="progress-bar bg-info" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
            '</div>' +
            '</div>'
    });

    var codes = [{key:"200 OK",val:200},
    			{key:"201 Created",val:201},
    			{key:"204 No Content",val:204},
    			{key:"400 Bad Request",val:400},
    			{key:"401 Unauthorized",val:401},
    			{key:"403 Forbidden",val:403},
    			{key:"404 Not Found",val:404},
    			{key:"500 Internal Server Error",val:500}];
    var ctypes = ["application/json","application/xml","text/json","text/xml"];
    var encodings = ["UTF-8","UTF-16"];

    $("input[name='mock-id-strategy']").change(function(){
        if ($(this).val() === 'Auto') {
              $("#mock-form-mock-id").prop("readonly", true);
        } else if ($(this).val() === 'Custom') {
              $("#mock-form-mock-id").prop("readonly", false);
        }
    });

    var option = '';
    for (var i = 0; i < codes.length; i++){
       option += '<option value="'+ codes[i].val + '">' + codes[i].key + '</option>';
    }
    $('#mock-form-success-response-status-code').append(option);
    $('#mock-form-failure-response-status-code').append(option);

    option = '';
    for (var i = 0; i < ctypes.length; i++){
       option += '<option value="'+ ctypes[i] + '">' + ctypes[i] + '</option>';
    }
    $('#mock-form-response-content-type').append(option);

    option = '';
    for (var i = 0; i < encodings.length; i++){
        option += '<option value="'+ encodings[i] + '">' + encodings[i] + '</option>';
     }
    $('#mock-form-response-encoding').append(option);

    var wrapper = $(".mock-form-header-template");
    var headerCounter = 0;
    $(".addButton").click(function(){
        $(wrapper).append('<div class="form-inline"><br><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" name="headers['+headerCounter+'][name]"  placeholder="Header Name"/><div class="btn-light mb-2 mr-sm-2">:</div><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" name="headers['+headerCounter+'][value]" placeholder="Header Value" /> <button type="button" class="btn btn-danger btn-sm removeButton">Delete</button></div>'); //add input box
        headerCounter++;
    });
    $(wrapper).on("click",".removeButton", function(){
        $(this).parent('div').remove();
    });

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

                // skip invalid keys
                if(!patterns.validate.test(this.name)){
                    return;
                }

                var k,
                    keys = this.name.match(patterns.key),
                    merge = this.value,
                    reverse_key = this.name;

                while((k = keys.pop()) !== undefined){

                    // adjust reverse_key
                    reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                    // push
                    if(k.match(patterns.push)){
                        merge = self.build([], self.push_counter(reverse_key), merge);
                    }

                    // fixed
                    else if(k.match(patterns.fixed)){
                        merge = self.build([], k, merge);
                    }

                    // named
                    else if(k.match(patterns.named)){
                        merge = self.build({}, k, merge);
                    }
                }

                json = $.extend(true, json, merge);
            });

            return json;
        };
    })(jQuery);

    $("#copy-mock-request-url").click(function(){
        var copyText = document.getElementById("mock-request-url");
        copyText.select();
        copyText.setSelectionRange(0, 99999); /*For mobile devices*/
        document.execCommand("copy");
    });

    $("#mockForm").submit(function(e) {
    	e.preventDefault();
    	var request = JSON.stringify($(this).serializeObject());
    	var action = $(document.activeElement).attr('value');
    	var addAction = "ADD";
    	var modifyAction = "MODIFY";
        if (action == addAction){
            var notify = $.notify({ message: 'Creating New Mock Request', icon: "fas fa-info-circle"});

            $.ajax({
                    url: '/api/addMock',
                    type: 'POST',
                    contentType: "application/json",
                    data: request,
                    success: function(data) {
                        $('#mockRequestModal').modal('hide');
                        setTimeout(function() {
                            notify.update('icon', 'far fa-check-circle');
                            notify.update('message', 'New Mock Request Created Successfully');
                        }, 2000);
                    	setTimeout(function() {
                            location.reload(true);
                        }, 2000);
                    },
        	        error : function(e) {
        	            $('#mockRequestModal').modal('hide');
        				setTimeout(function() {
                            notify.update('icon', 'fas fa-bug');
                            notify.update('message', 'Issue while Creating New Mock Request');
                        }, 2000);
        			}
            });
        } else if (action == modifyAction) {
            var notify = $.notify({ message: 'Modifying Mock Request', icon: "fas fa-info-circle"});

            $.ajax({
                url: '/api/modifyMock',
                type: 'POST',
                contentType: "application/json",
                data: request,
                success: function(data) {
                    $('#mockRequestModal').modal('hide');
                    setTimeout(function() {
                        notify.update('icon', 'far fa-check-circle');
                        notify.update('message', 'Mock Request Modified Successfully');
                    }, 2000);
                    setTimeout(function() {
                        location.reload(true);
                    }, 2000);
                },
                error : function(e) {
                    $('#mockRequestModal').modal('hide');
                    setTimeout(function() {
                        notify.update('icon', 'fas fa-bug');
                        notify.update('message', 'Issue while Modifying Mock Request');
                    }, 2000);
                }
            });
        }
    });

    $("#mockRequestModal").on("hidden.bs.modal", function() {
        $('#mockRequestModal #mock-form-modal-title').text("");
        $('#mockRequestModal #mock-request-url').val("");
        $('#mockRequestModal #copy-mock-request-url').prop('disabled', true);
        $('#mockRequestModal #mock-form-request-type').val("").prop('disabled', false);
        $('#mockRequestModal #mock-form-mock-id').val("").prop('readonly', true);
        $('#mockRequestModal #mock-form-success-response-status-code').val("").prop('disabled', false);
        $('#mockRequestModal #mock-form-failure-response-status-code').val("").prop('disabled', false);
        $('#mockRequestModal #mock-form-response-content-type').val("").prop('disabled', false);
        $('#mockRequestModal #mock-form-response-encoding').val("").prop('disabled', false);
        $(".mock-form-header-template").empty();
        $('#mockRequestModal #mock-form-success-response-body').val("").prop('readonly', false);
        $('#mockRequestModal #mock-form-failure-response-body').val("").prop('readonly', false);
        $('#mockRequestModal #mock-form-modal-footer').html("<button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>");
    });

    $("button[name=deleteMockBtn]").click(function(e) {
        var notify = $.notify({ message: 'Deleting Mock Request', icon: "fas fa-info-circle"});
        var mockId = $(this).attr('mockId');

        $.ajax({
            url: '/api/deleteMock/' + mockId,
            type: 'GET',
            success: function(data) {
                setTimeout(function() {
                    notify.update('icon', 'far fa-check-circle');
                    notify.update('message', 'Mock Request Deleted Successfully');
                }, 2000);
                setTimeout(function() {
                    location.reload(true);
                }, 2000);
            },
            error : function(e) {
        	    setTimeout(function() {
                    notify.update('icon', 'far fas fa-bug');
                    notify.update('message', 'Issue while Deleting Mock Request');
                }, 2000);
        	}
        });
    });

    $("button[name=addMockBtn]").click(function(e) {
        headerCounter = 0;
        $('#mockRequestModal').modal('show');
        $('#mockRequestModal #mock-form-modal-title').text("Add New Mock Request");
        $('#mockRequestModal #mock-form-modal-footer').append("<button type='submit' value='ADD' class='btn btn-primary'>Add</button>");
    });

    $("button[name=viewMockBtn]").click(function(e) {
        var mockId = $(this).attr('mockId');
        var baseUrl = window.location.origin;

        $.ajax({
            url: '/api/getMock/' + mockId,
            type: 'GET',
            success: function(data) {
                $('#mockRequestModal').modal('show');
                $('#mockRequestModal #mock-request-url').val(baseUrl + "/mock/" + data.mockId);
                $('#mockRequestModal #copy-mock-request-url').prop('disabled', false);
                $('#mockRequestModal #mock-form-modal-title').text("Mock Request - " + data.mockId);
                $('#mockRequestModal #mock-form-request-type').val(data.requestType).prop('disabled', true);
                $('#mockRequestModal #mock-form-mock-id').val(data.mockId).prop('readonly', true);
                $('#mockRequestModal #mock-form-success-response-status-code').val(data.successResponseStatusCode).prop('disabled', true);
                $('#mockRequestModal #mock-form-failure-response-status-code').val(data.failureResponseStatusCode).prop('disabled', true);
                $('#mockRequestModal #mock-form-response-content-type').val(data.contentType).prop('disabled', true);
                $('#mockRequestModal #mock-form-response-encoding').val(data.encoding).prop('disabled', true);

                var wrapper = $(".mock-form-header-template");
                $(wrapper).empty();
                $.each(data.headers, function (key, value) {
                    $(wrapper).append('<div class="form-inline"><br><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" placeholder="Header Name" value="'+ value.name +'" readonly/><div class="btn-light mb-2 mr-sm-2">:</div><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" placeholder="Header Value" value="'+ value.value +'" readonly/></div>'); //add input box
                });

                $('#mockRequestModal #mock-form-success-response-body').val(data.successResponseBody).prop('readonly', true);
                $('#mockRequestModal #mock-form-failure-response-body').val(data.failureResponseBody).prop('readonly', true);
            },
            error : function(e) {
                $.notify({ message: 'Issue while Viewing Mock Request', icon: "fas fa-bug"});
            }
        });
    });

    $("button[name=modifyMockBtn]").click(function(e) {
        var mockId = $(this).attr('mockId');
        var baseUrl = window.location.origin;

        $.ajax({
            url: '/api/getMock/' + mockId,
            type: 'GET',
            success: function(data) {
                $('#mockRequestModal').modal('show');
                $('#mockRequestModal #mock-request-url').val(baseUrl + "/mock/" + data.mockId);
                $('#mockRequestModal #copy-mock-request-url').prop('disabled', false);
                $('#mockRequestModal #mock-form-modal-title').text("Mock Request - " + data.mockId);
                $('#mockRequestModal #mock-form-request-type').val(data.requestType);
                $('#mockRequestModal #mock-form-mock-id').attr("name", "mockId");
                $('#mockRequestModal #mock-form-mock-id').val(data.mockId);
                $('#mockRequestModal #mock-form-success-response-status-code').val(data.successResponseStatusCode);
                $('#mockRequestModal #mock-form-failure-response-status-code').val(data.failureResponseStatusCode);
                $('#mockRequestModal #mock-form-response-content-type').val(data.contentType);
                $('#mockRequestModal #mock-form-response-encoding').val(data.encoding);

                var wrapper = $(".mock-form-header-template");
                $(wrapper).empty();
                headerCounter = 0;
                $.each(data.headers, function (key, value) {
                    $(wrapper).append('<div class="form-inline"><br><input type="hidden" name="headers['+headerCounter+'][id]" value="'+ value.id +'" /><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" name="headers['+headerCounter+'][name]"  placeholder="Header Name" value="'+ value.name +'" /><div class="btn-light mb-2 mr-sm-2">:</div><input type="text" class="form-control form-control-sm mb-2 mr-sm-2" name="headers['+headerCounter+'][value]" placeholder="Header Value" value="'+ value.value +'" /> <button type="button" class="btn btn-danger btn-sm removeButton">Delete</button></div>'); //add input box
                    headerCounter++;
                });

                $('#mockRequestModal #mock-form-success-response-body').val(data.successResponseBody);
                $('#mockRequestModal #mock-form-failure-response-body').val(data.failureResponseBody);
                $('#mockRequestModal #mock-form-modal-footer').append("<button type='submit' value='MODIFY' class='btn btn-primary'>Modify</button>");
            },
            error : function(e) {
                $.notify({ message: 'Issue while Viewing Mock Request', icon: "far fa-check-circle"});
            }
        });
    });

    var notify;
    $(".editable-text-full").editable("/api/updateMockDelay", {
        id : "mockId",
        type : "text",
        cancel : '<button type="cancel" class="btn btn-sm btn-danger"><i class="fas fa-times"></i></button>',
        cssclass : 'custom-class',
        inputcssclass : 'form-control form-control-sm',
        cancelcssclass : 'btn btn-sm btn-danger',
        submitcssclass : 'btn btn-sm btn-success',
        maxlength : 200,
        select : true,
        submit : '<button type="submit" class="btn btn-sm btn-success"><i class="fas fa-save"></i></button>',
        tooltip : "Click to Modify",
        width : 80,
        onsubmit : function() { notify = $.notify({ message: 'Updating Mock Request Status', icon: "fas fa-info-circle"}); },
        callback : function(value, settings) {
            setTimeout(function() {
                notify.update('icon', 'far fa-check-circle');
                notify.update('message', 'Mock Request Delay Updated Successfully');
            }, 2000);
            return(value);
        }
    });

    $("input[name=mockServiceResponseStatus]").change(function(e) {
        var notify = $.notify({ message: 'Updating Mock Request Status', icon: "fas fa-info-circle"});
        var mockId = $(this).attr('mockId');
        var status = $(this).prop('checked');

        var data = { "mockId": mockId, "value": status };
        $.ajax({
            url: '/api/updateMockResponseStatus',
            type: 'POST',
            data: data,
            success: function(data) {
                setTimeout(function() {
                    notify.update('icon', 'far fa-check-circle');
                    notify.update('message', 'Mock Request Status Updated Successfully');
                }, 2000);
            },
            error : function(e) {
                setTimeout(function() {
                    notify.update('icon', 'fas fa-bug');
                    notify.update('message', 'Issue while updating Mock Request Status');
                }, 2000);
                setTimeout(function() {
                    location.reload(true);
                }, 3000);
            }
        });
    });
});