var express = require("express")
var bodyParser = require('body-parser');
var app = express()
const mongoose = require('mongoose');
const accountDB = mongoose.createConnection('mongodb://localhost:27017/accountDB')
const cityDB = mongoose.createConnection('mongodb://localhost:27017/cityDB')
const airportDB = mongoose.createConnection('mongodb://localhost:27017/airportDB')
const port = 8000;

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use(express.json())

const citySchema = new mongoose.Schema({
    cityid:String,
    iata:String
  })

const City = cityDB.model('City', citySchema);

const airportSchema = new mongoose.Schema({
    airportname:String,
    airportiata:String,
    cityiata:String
  })

const flightSchema = new mongoose.Schema({
    airportname_dep:String,
    airportiata_dep:String,
    cityiata_dep:String,
    airportname_arr:String,
    airportiata_arr:String,
    cityiata_arr:String,
    date:String,
    dep_time:String,
    arr_time:String,
    flight_num:String,
    city_dep:String,
    city_arr:String
  })

const Flight = accountDB.model('Flight', flightSchema);

const Airport = airportDB.model('Airport', airportSchema);

const accountSchema = new mongoose.Schema({
  userid:String,
  name:String,
  Flights:{type: [flightSchema], default: []}
})

const Account = accountDB.model('Account', accountSchema);


const server = app.listen(port, () => {
    console.log(`App listening on port ${port}`)
})

//start the server
app.get('/',(req, res) => {
    console.log('server is being accessed')
    res.send('server is active')
})

//save the cities
app.post("/city",(req,res)=>{
    var data = req.body
    data.forEach(element => {
        var cityid = element.nameCity
        var iata = element.codeIataCity
        console.log(cityid)
        console.log(iata)
        const newCity = new City({cityid,iata})
        newCity.save(err => err && console.log(err))
    });
    res.status(200).json("sucess")
})

//get city iata
app.route("/city/:cityID").get((req,res,next) => {
    console.log(req.params.cityID)
    City.findOne({cityid: req.params.cityID},(err,city) => {
      if (err) return next(err);
      if (!city) return res.status(404).end('city not found');
      return res.json(city);
    })
  })

  //save airports data
  app.post("/airport",(req,res)=>{
    var data = req.body
    data.forEach(element => {
        var airportname = element.nameAirport
        var airportiata = element.codeIataAirport
        var cityiata = element.codeIataCity
        console.log(airportname)
        console.log(airportiata)
        const newAirport = new Airport({airportname,airportiata,cityiata})
        newAirport.save(err => err && console.log(err))
    });
    res.status(200).json("sucess")
  })

//get airport iata
app.route("/airport/:cityiata").get((req,res,next) => {
    console.log(req.params.cityiata)
    Airport.find({cityiata: req.params.cityiata},(err,airport) => {
      if (err) return next(err);
      if (!airport||airport==[]) return res.status(404).end('airport not found');
      return res.json(airport);
    })
  })

app.post("/account",(req,res)=>{
  var data = req.body
  //console.log(data)
  var id = data.nameValuePairs.userid
  var name = data.nameValuePairs.name
  if(id && name){
    Account.find({userid: id},(err1,account) => {
      if (account.length == 0){
        const newAccount = new Account({userid:id,name:name})
        newAccount.save(err => err && console.log(err))
        res.status(200).json("sucess")
      }
      else{
        res.status(500).json("failed")
      }
    })
  }else{
    res.status(400).json("Missing the parameters")
    console.log(id)
    console.log(name)
  }
})

async function postFlight(path,body,callback){
  var data = body
  console.log(data)
  const userid = path
  console.log(userid)
  const leaveTime=data.leaveTime
  const arriveTime=data.arriveTime
  const fromIata=data.fromIata
  const toIata=data.toIata;
  const flightNum=data.flightNum;
  const dep_city = data.dep_city;
  const arr_city = data.arr_city;
  const date = data.date
  const airport_dep_name = data.airport_dep_name;
  const airport_arr_name = data.airport_arr_name;
  const dep_city_name = data.city_dep;
  const arr_city_name = data.city_arr;
  if(flightNum != null && date != null && fromIata != null){
    const newFlight = new Flight({airportname_dep:airport_dep_name,
                                  airportiata_dep:fromIata,
                                  cityiata_dep:dep_city,
                                  airportname_arr:airport_arr_name,
                                  airportiata_arr:toIata,
                                  cityiata_arr:arr_city,
                                  date:date,
                                  dep_time:leaveTime,
                                  arr_time:arriveTime,
                                  flight_num:flightNum,
                                  city_dep:dep_city_name,
                                  city_arr:arr_city_name})
    const pushitem = {Flights:newFlight};
    console.log(newFlight)
    const newaccount = await Account.findOneAndUpdate(
      {userid: userid},
      {$push: pushitem}
    )
    console.log(newaccount.Flights)
    if(newaccount){
      return callback(null,200,newaccount);
    }else{
      return callback(null,400,"failed");
    }
  }else{
    return callback(null,400,"Missing something");
  }
}


app.route('/flights/:userid')
.post(async (req,res) => {
  await postFlight(
    req.params.userid, req.body, 
    (err,status,returnData) => {
      if (err) console.log(err);
      res.status(status).json(returnData);
    })
})

app.route("/account/:userid").get((req,res,next) => {
  console.log(req.params.userid)
  Account.findOne({cityiata: req.params.userid},(err,account) => {
    if (err) return next(err);
    if (!account) return res.status(404).end('account not found');
    return res.json(account);
  })
})


