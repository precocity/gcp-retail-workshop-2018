from flask import Flask, jsonify, request

from recommendations import Recommendations

app = Flask(__name__)

rec_util = Recommendations()

DEFAULT_RECS = 5

@app.route('/recommendation', methods=['GET'])
def recommendation():
  """Given a user id, return a list of recommended item ids."""
  user_id = request.args.get('userId')
  num_recs = request.args.get('numRecs')

  # validate args
  if user_id is None:
    return 'No User Id provided.', 400
  if num_recs is None:
    num_recs = DEFAULT_RECS
  try:
    uid_int = int(user_id)
    nrecs_int = int(num_recs)
  except:
    return 'User id and number of recs arguments must be integers.', 400

  # get recommended articles
  rec_list = rec_util.get_recommendations(uid_int, nrecs_int)

  if rec_list is None:
    return 'User Id not found : %s' % user_id, 400

  json_response = jsonify({'articles': [str(i) for i in rec_list]})
  return json_response, 200


@app.route('/readiness_check', methods=['GET'])
def readiness_check():
  return '', 200

if __name__ == '__main__':
  app.run(host='127.0.0.1', port=8080, debug=True)