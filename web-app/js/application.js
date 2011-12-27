var Ajax;
if (Ajax && (Ajax != null)) {
	Ajax.Responders.register({
	  onCreate: function() {
        if($('spinner') && Ajax.activeRequestCount>0)
          Effect.Appear('spinner',{duration:0.5,queue:'end'});
	  },
	  onComplete: function() {
        if($('spinner') && Ajax.activeRequestCount==0)
          Effect.Fade('spinner',{duration:0.5,queue:'end'});
	  }
	});
}

/**
 * move selected Options from one select box to another
 */
function moveSelectedOptions(fromElementId, toElementId)
{
	// finding objects
	var fromObj = document.getElementById(fromElementId);
	var toObj = document.getElementById(toElementId);
	var ret=false;

	// is this a select box
	if((fromObj && fromObj.type=="select-multiple")
		&& (toObj&&toObj.type=="select-multiple")) {
			ret=true;
	}

	// 2 multiple select boxes found
	if(ret){
		//========================
		// moving selected options
		//========================
		// iterating all options from source select box
		for(var i=0;i<fromObj.options.length;i++){
			// is this a selected option
			if(fromObj.options[i].selected){
				// creating new option in target select box
				toObj.options[toObj.options.length] = new Option(fromObj.options[i].text
																,fromObj.options[i].value);
				// delete current option
				fromObj.options[i] = null;
				i--;
			}
		}
	}

	return ret;
}

/**
 * move all Options from one select box to another
 */
function moveAllOptions(fromElementId, toElementId)
{
	var ret=false;

	ret=selectAllOptions(fromElementId);
	if (ret){
		ret=moveSelectedOptions(fromElementId, toElementId);
	}

	return ret;
}

/**
 * select all Options from one select box
 */
function selectAllOptions(selectId)
{
	// finding objects
	var selectObj = document.getElementById(selectId);
	var ret=false;

	// is this a select box
	if(selectObj != undefined && selectObj.type=="select-multiple"){
		ret=true;
	}

	if(ret) {
		for(var i=0;i<selectObj.options.length;i++){
			selectObj.options[i].selected = true;
		}
	}

	return ret;
}
