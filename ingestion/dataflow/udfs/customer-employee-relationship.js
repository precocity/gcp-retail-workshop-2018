function transform(line) {
	var values = line.split(',');

	var obj = new Object();
	obj.employee_relationship_id = values[0].replace(/^"/, "").replace(/"$/, "");
	obj.customer_id = values[1].replace(/^"/, "").replace(/"$/, "");
	obj.employee_id = values[2].replace(/^"/, "").replace(/"$/, "");
	obj.relationship_type = values[3].replace(/^"/, "").replace(/"$/, "");
	obj.relationship_start_date = values[4].replace(/^"/, "").replace(/"$/, "");
	obj.created_timestamp = values[5].replace(/^"/, "").replace(/"$/, "");
	obj.updated_timestamp = values[6].replace(/^"/, "").replace(/"$/, "");

	var jsonString = JSON.stringify(obj);

	return jsonString;
}