/** * START - Check password Strength ** */

function checkStrength($strengthLabel,$password){
	var strength = 0;
	var minLength = 8;
	var number = /([0-9])/;
    var alphabets = /([a-zA-Z])/;
    var upperCase= new RegExp('[A-Z]');
    var lowerCase= new RegExp('[a-z]');
    var special_characters = /([~,!,@,#,$,%,^,&,*,-,_,+,=,?,>,<])/;
    
    if ($password.length >= minLength && 
    		$password.match(number) && 
    		$password.match(alphabets) && 
    		$password.match(special_characters) && 
    		$password.match(upperCase) && 
    		$password.match(lowerCase)) {
    	 $strengthLabel.hide();
         return true;
    } else {
        $strengthLabel.html("Password must be at least 8 characters in length and contain the following characters: (a) uppercase letters, (b) lowercase letters, (c) numbers, and (d) special characters.");
        $strengthLabel.show();
        return false;
    }
}
/** * END - Check password Strength ** */