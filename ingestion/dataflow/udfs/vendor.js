function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.vendor_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.vendor_name = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[3].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}