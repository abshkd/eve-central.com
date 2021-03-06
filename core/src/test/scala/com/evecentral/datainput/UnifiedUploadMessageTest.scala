package com.evecentral.datainput

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class UnifiedUploadMessageTest extends FunSuite with ShouldMatchers {


	val historyMessage =
		"""
		  {
 "resultType" : "history",
 "version" : "0.1",
 "uploadKeys" : [
   { "name" : "emk", "key" : "abc" },
   { "name" : "ec" , "key" : "def" }
 ],
 "generator" : { "name" : "Yapeal", "version" : "11.335.1737" },
 "currentTime" : "2011-10-22T15:46:00+00:00",
 "columns" : ["date","orders","quantity","low","high","average"],
 "rowsets" : [
   {
     "generatedAt" : "2011-10-22T15:42:00+00:00",
     "regionID" : 10000065,
     "typeID" : 11134,
     "rows" : [
       ["2011-12-03T00:00:00+00:00",40,40,1999,499999.99,35223.50],
       ["2011-12-02T00:00:00+00:00",83,252,9999,11550,11550]
     ]
   }
 ]
}
	""".stripMargin
  val message = """
  {
  "resultType" : "orders",
  "version" : "0.1alpha",
  "uploadKeys" : [
    { "name" : "emk", "key" : "abc" },
    { "name" : "ec" , "key" : "def" }
  ],
  "generator" : { "name" : "Yapeal", "version" : "11.335.1737" },
  "currentTime" : "2011-10-22T15:46:00+00:00",
  "columns" : ["price","volRemaining","range","orderID","volEntered","minVolume","bid","issueDate","duration","stationID","solarSystemID"],
  "rowsets" : [
    {
      "generatedAt" : "2011-10-22T15:43:00+00:00",
      "regionID" : 10000065,
      "typeID" : 11134,
      "rows" : [
        [8999,1,32767,2363806077,1,1,false,"2011-12-03T08:10:59+00:00",90,60008692,30005038],
        [11499.99,10,32767,2363915657,10,1,false,"2011-12-03T10:53:26+00:00",90,60006970,30005038],
        [11500,48,32767,2363413004,50,1,false,"2011-12-02T22:44:01+00:00",90,60006967,30005039]
      ]
    },
    {
      "generatedAt" : "2011-10-22T15:42:00+00:00",
      "regionID" : 10000065,
      "typeID" : 11135,
      "rows" : [
        [8999,1,32767,2363806077,1,1,false,"2011-12-03T08:10:59+00:00",90,60008692,30005038],
        [11499.99,10,32767,2363915657,10,1,false,"2011-12-03T10:53:26+00:00",90,60006970,30005038],
        [11500,48,32767,2363413004,50,1,false,"2011-12-02T22:44:01+00:00",90,60006967,30005039]
      ]
    }
  ]
}"""

  test("Simple parse") {
    val i = UnifiedParser(message).get.asInstanceOf[UnifiedUploadMessage]

    i.columns should equal (List("price","volRemaining","range","orderID","volEntered","minVolume","bid","issueDate","duration","stationID","solarSystemID"))
		i.rowsets should have length (2)
		i.rowsets(0).valid should equal (true)
		i.rowsets(0).regionId should equal (10000065)
  }

	test("History parse") {
		val i = UnifiedParser(historyMessage).get.asInstanceOf[UnifiedHistoryMessage]
		i.rowsets should have length(1)
		i.rowsets(0).regionId should equal (10000065)
	}

}
