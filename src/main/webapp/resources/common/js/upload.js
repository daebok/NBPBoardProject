/* 
 * 파일 업로드에 관련된 자바스크립트 파일
 */
function checkImageType(fileName) {
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern);
}

function getOriginalName(fileName) {
	if (checkImageType(fileName)) { 
		return;
	}
	var idx = fileName.indexOf("_") + 1;
	return fileName.substr(idx);
}

function getImageLink(fileName) { 
	if (!checkImageType(fileName)) {
		return;
	}
	var front = fileName.substr(0, 12); 
	var end = fileName.substr(14);
	return front + end;
}
