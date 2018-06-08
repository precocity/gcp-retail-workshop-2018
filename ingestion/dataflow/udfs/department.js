function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.department_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.division_id = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.department_name = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[4].replace(/^"/, "").replace(/"$/, "");
	
	var jsonString = JSON.stringify(obj);

	return jsonString;
}