	$(function() {
		$('#sub').click(function() {
			var flg = "1";
			$('#form').submit( function( e ) {
				$("#keihi_name").html($("#cost_name").val());
				$("#keihi_kubun").html($("#cost_division option:selected").text());
				$("#keihi_money").html($("#bonus_point_use").val());
				$("#keihi_detail").html($("#detail").val());
				$("#keihi_detail").html(flg);
		    	$.magnificPopup.open({
		    		  items: {
		    		    src: '#popup'
		    		  },
		    		  type: 'inline'
		    		});
		    	if (flg == "1"){
		    		return true;	
		    	}
	        	return false;
	    	} );
		});
	} );
	$(function() {
		$('#canselSub').click(function() {
			var flg = "0";
			$('#form').submit( function( e ) {
				$("#keihi_name").html($("#cost_name").val());
				$("#keihi_kubun").html($("#cost_division option:selected").text());
				$("#keihi_money").html($("#bonus_point_use").val());
				$("#keihi_detail").html($("#detail").val());
				$("#keihi_detail").html(flg);
		    	$.magnificPopup.open({
		    		  items: {
		    		    src: '#popup'
		    		  },
		    		  type: 'inline'
		    		});
		    	if (flg == "1"){
		    		return true;	
		    	}
	        	return false;
	    	} );
		});
	} );
