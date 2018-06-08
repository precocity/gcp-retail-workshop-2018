function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.channel_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.channel_name = values[1].replace(/^"/, "").replace(/"$/, "");
	
	var jsonString = JSON.stringify(obj);

	return jsonString;
}