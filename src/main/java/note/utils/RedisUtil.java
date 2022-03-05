package note.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 */
@Component
public class RedisUtil {
	private final RedisTemplate<String, Object> redisTemplate;

	public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	public long ttl(String key) {
		return redisTemplate.getExpire(key);
	}

	public void expire(String key, long timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
	}

	public long incr(String key, long delta) {
		return redisTemplate.opsForValue().increment(key, delta);
	}

	public Set<String> keys(String pattern) {
		return redisTemplate.keys(pattern);
	}

	public void del(String key) {
		redisTemplate.delete(key);
	}

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public void set(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public Long size(String key) {
		return redisTemplate.opsForValue().size(key);
	}

	public boolean hasHkey(String key, Object field) {
		return redisTemplate.opsForHash().hasKey(key, field);
	}

	public void hset(String key, Object field, Object value) {
		redisTemplate.opsForHash().put(key, field, value);
	}


	/**
	 * 实现命令：hHSIZE key，将哈希表 key中数据
	 *
	 * 
	 */
	public Long hsize(String key) {
		return redisTemplate.opsForHash().size(key);
	}

	/**
	 * 实现命令：HGET key field，返回哈希表 key中给定域 field的值
	 *
	 * 
	 * 
	 * 
	 */
	public Object hget(String key, Object field) {
		return redisTemplate.opsForHash().get(key, field);
	}

	/**
	 * 实现命令：HDEL key field [field ...]，删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
	 *
	 * 
	 * 
	 */
	public void hdel(String key, Object... fields) {
		redisTemplate.opsForHash().delete(key, fields);
	}

	/**
	 * 实现命令：HGETALL key，返回哈希表 key中，所有的域和值。
	 *
	 * 
	 * 
	 */
	public Object hgetall(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	// List（列表）

	/**
	 * 实现命令：LPUSH key value，将一个值 value插入到列表 key的表头
	 *
	 * 
	 * 
	 * 
	 */
	public long lpush(String key, Object value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 截取集合元素长度，保留长度内的数据
	 *
	 * 
	 * 
	 * 
	 */
	public void ltrim(String key, long start, long end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	/**
	 * 实现命令：LPUSHALL key value 把多个value值存入到list集合中
	 *
	 * 
	 * 
	 * 
	 */
	public long lpushAll(String key, Object... value) {

		return redisTemplate.opsForList().leftPushAll(key, value);
	}


	/**
	 * 从存储在键中的列表中删除等于值的元素的第一个计数事件。
	 * count> 0：删除等于从左到右移动的值的第一个元素；
	 * count< 0：删除等于从右到左移动的值的第一个元素；
	 * count = 0：删除等于value的所有元素。
	 *
	 * 
	 * 
	 * 
	 * 
	 */
	public long lremove(String key, long count, Object value) {
		return redisTemplate.opsForList().remove(key, 0, value);
	}

	/**
	 * 实现命令：LPOP key，移除并返回列表 key的头元素。
	 *
	 * 
	 * 
	 */
	public Object lpop(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 实现命令：RPUSH key value，将一个值 value插入到列表 key的表尾(最右边)。
	 *
	 * 
	 * 
	 * 
	 */
	public long rpush(String key, Object value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 实现命令：RANGE key start stop , 将列表key中的值下标从start到stop取出
	 *
	 * 
	 * 
	 * 
	 * 
	 */
	public Object range(String key, long start, long stop) {
		return redisTemplate.opsForList().range(key, start, stop);
	}

	/**
	 * 实现命令： LLEN key,列表中key的长度
	 *
	 * 
	 * 
	 */
	public long llen(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 任务队列
	 * 命令实现 RPOPLPUSH sourceKey destinationKey，移除列表的最后一个元素，并将该元素添加到另一个列表并返回
	 *
	 * 
	 * 
	 */
	public void rpoplpush(String sourceKey, String destinationKey) {
		redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey);
	}


}