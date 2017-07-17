function checkImageType(fileName) {
	var pattern = /jpg|gif|png|jpeg/i;
	return fileName.match(pattern);
}

function getOriginalName(fileName) {
	if (checkImageType(fileName)) { // if image file
		return;
	}
	var idx = fileName.indexOf("_") + 1;
	return fileName.substr(idx);
}

/* thumbnail click -> original imgae */
function getImageLink(fileName) { 
	if (!checkImageType(fileName)) { // if not image file
		return;
	}
	var front = fileName.substr(0, 12); // 년원일 경로 추출
	var end = fileName.substr(14); // 년원일 경로와 s_를 제거한 원본 파일명을 추출
	return front + end;
}

/* file info */
function getFileInfo(fullName){
    var fileName, imgsrc, getLink, fileLink;
    if(checkImageType(fullName)){ // if image file
        imgsrc = "/upload/displayFile?fileName="+fullName; // image file path
       
        fileLink = fullName.substr(14); // upload file name

        var front = fullName.substr(0, 12); // date
        var end = fullName.substr(14); 
        console.log(end);

        getLink = "/upload/displayFile?fileName="+front+end;
    
    } else { // if not image
        fileLink = fullName.substr(12);
        getLink = "/displayFile?fileName="+fullName;
    }

    fileName = fileLink.substr(fileLink.indexOf("_")+1);

    return {fileName:fileName, imgsrc:imgsrc, getLink:getLink, fullName:fullName};
}
