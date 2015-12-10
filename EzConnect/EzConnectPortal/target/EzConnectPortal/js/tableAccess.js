				
	        		    $(document).ready(function() {
	        		        $.ajax(
	        		      		{
	        			        	url : "jsonReturn",
	        			        	
	        			        	dataType: "json",
	        			        		success: function(data)
		        			        	{
		        			        		//data = $.parseJSON(data);
		        			        		$(function() {
		        			        			
		        			        		    $.each(data, function(i, item) {
		        			        		        var $tr = $('#reportBody').append(
		        			        		            $('<tr>').append(
		        			       		        		$('<td>').text(item.ezConnectId),
		        			        		            $('<td>').text(item.Callee1),
		        			        		            $('<td>').text(item.Callee2),
		        			        		            $('<td>').text(item.createdDate),
		        			        		            $('<td>').text(item.updatedStatus),
		        			        		            $('<td>').text(item.callDuration),
		        			        		            $('<td>').text(item.recording),
		        			        		            $('<td>').html("<a href="+'report/downloadMedia/'+item.ezConnectId+">Link</a>")
		        			        		      )  ); //.appendTo('#records_table');
		        			        		        console.log($tr.wrap('<p>').html());
		        			        		    });
		        			        		});
		        			        	},
		        			        	
	        		      		});
	        		        $username="meera";
	        		        });