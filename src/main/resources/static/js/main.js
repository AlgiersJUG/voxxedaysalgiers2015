$(document).ready(function () {
   console.log("Ready");

    $.get("/photos", function (photos) {
        for(var i= 0,len=photos.length; i<len; i++) {
            var photoId = photos[i].name;
            console.log(photoId + photos[i].author + photos[i].tags);
            $.get("/photos/url/"+photoId, function (absolutePhotoUrl) {
                $("#photoStream").prepend('<img id="theImg" src="absolutePhotoUrl" />');
            })

        }
    })
});
